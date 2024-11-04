package carrotmoa.carrotmoa.service;

import carrotmoa.carrotmoa.entity.AccommodationImage;
import carrotmoa.carrotmoa.repository.AccommodationImageRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@Slf4j
public class AccommodationImageService {
    private final AccommodationImageRepository accommodationImageRepository;
    private final AwsS3AccommodationService awsS3AccommodationService;
//    private final AwsS3Utils awsS3Utils;

    public AccommodationImageService(AccommodationImageRepository accommodationImageRepository, AwsS3AccommodationService awsS3AccommodationService) {
        this.accommodationImageRepository = accommodationImageRepository;
        this.awsS3AccommodationService = awsS3AccommodationService;
    }

    @Transactional
    public void updateAccommodationImages(Long accommodationId, List<MultipartFile> newImages, List<String> existingImageUrls) throws IOException {
        // 기존 이미지 삭제
        deleteExistingImages(existingImageUrls);

        // 새 이미지 저장
        saveAccommodationImages(accommodationId, newImages);
    }

    @Transactional
    public void saveAccommodationImages(Long accommodationId, List<MultipartFile> images) throws IOException {
        if (images != null && !images.isEmpty()) {
            for (int i = 0; i < images.size(); i++) {
                String imageUrl = uploadAndSaveImage(accommodationId, images.get(i), i);
            }
        }
    }

    // 기존 이미지 삭제
    private void deleteExistingImages(List<String> existingImageUrls) throws IOException {
        if (existingImageUrls != null && !existingImageUrls.isEmpty()) {
            for (String imageUrl : existingImageUrls) {
                awsS3AccommodationService.deleteImageFromUrl(imageUrl); // S3에서 이미지 삭제
                log.info("Deleted image from S3: {}", imageUrl); // 삭제 로그 추가
                // 데이터베이스에서 이미지 메타데이터 삭제 필요시 추가 로직
            }
        }
    }

    // s3 직접 업로드 + url 데베에 저장
    private String uploadAndSaveImage(Long accommodationId, MultipartFile image, int order) throws IOException {
        String imageUrl = awsS3AccommodationService.uploadRoomImage(accommodationId, image);
        saveImageMetadata(accommodationId, imageUrl, order);
        return imageUrl;
    }

    // url만 데베에 저장
    private void saveImageMetadata(Long accommodationId, String imageUrl, int order) {
        AccommodationImage accommodationImage = AccommodationImage.builder()
                .accommodationId(accommodationId)
                .imageUrl(imageUrl)
                .imageOrder(order)
                .build();
        accommodationImageRepository.save(accommodationImage);
    }

    private String extractFilePathFromUrl(String url) {
        // URL을 슬래시('/')로 분리
        String[] parts = url.split("/");

        // room/42/와 파일 이름을 결합
        return parts[parts.length - 2] + "/" + parts[parts.length - 1];
    }

}
