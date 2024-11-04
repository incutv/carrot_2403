package carrotmoa.carrotmoa.service;

import carrotmoa.carrotmoa.util.AwsS3Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.exception.SdkException;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;

import java.io.IOException;
import java.util.UUID;

@Service
@Slf4j
public class AwsS3AccommodationService {

    private final AwsS3Utils awsS3Utils;

    private final S3Client s3Client;


    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    @Value("${cloud.aws.region.static}")
    private String region;

    public AwsS3AccommodationService(AwsS3Utils awsS3Utils, S3Client s3Client) {
        this.awsS3Utils = awsS3Utils;
        this.s3Client = s3Client;
    }

    // 특정 roomId 폴더에 이미지를 S3에 업로드하는 메서드
    public String uploadRoomImage(Long roomId, MultipartFile file) throws IOException {
        String fileName = "room/" + roomId + "/" + UUID.randomUUID() + awsS3Utils.getFileExtension(file.getOriginalFilename());

        // S3에 이미지 업로드
        awsS3Utils.uploadImage(fileName, file);

        // 업로드된 이미지 URL 반환
        String imageUrl = awsS3Utils.getImageUrl(fileName);

        // 성공적으로 저장된 경우 로그 출력
        log.info("Uploaded image to S3: {}", imageUrl);

        return imageUrl; // 업로드된 이미지 URL 반환
    }

    public void deleteImageFromUrl(String url) throws IOException {
        // URL에서 파일 경로 추출
        String filePath = extractFilePathFromUrl(url);

        // S3에서 객체 삭제 요청 생성
        DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                .bucket(bucketName) // 버킷 이름
                .key(filePath) // 파일 경로
                .build();

        try {
            s3Client.deleteObject(deleteObjectRequest); // S3에서 객체 삭제
            log.info("Deleted image: {}", filePath); // 삭제 성공 로그
        } catch (SdkException e) {
            log.error("Error deleting file from S3: {}", filePath, e); // 삭제 실패 로그
            throw new IOException("Error deleting file from S3: " + filePath, e); // 예외 발생
        }
    }

    String extractFilePathFromUrl(String url) {
        // URL을 슬래시('/')로 분리
        String[] parts = url.split("/");

        // room/42/와 파일 이름을 결합
        return parts[parts.length - 3] + "/" + parts[parts.length - 2] + "/" + parts[parts.length - 1];
    }


}