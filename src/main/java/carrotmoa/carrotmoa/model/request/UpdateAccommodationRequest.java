package carrotmoa.carrotmoa.model.request;

import carrotmoa.carrotmoa.entity.Accommodation;
import carrotmoa.carrotmoa.entity.AccommodationSpace;
import carrotmoa.carrotmoa.entity.Post;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Slf4j
public class UpdateAccommodationRequest {
    private static final Long SERVICE_ID = 8L;

    private Long userId;
    private String title;
    private Integer totalArea;
    private String roadAddress;
    private String lotAddress;
    private String detailAddress;
    private Integer floor;
    private Integer totalFloor;
    private BigDecimal price;
    private String content;
    private String transportationInfo;

    // 관계 테이블 리스트 (Space 테이블에 방, 화장실, 거실, 주방 순서로 담겨 있어야)
    private List<AccommodationSpaceRequest> accommodationSpaces = new ArrayList<>(); // 초기화
    private List<Long> amenityIds = new ArrayList<>();
    private List<MultipartFile> images; // 업로드된 이미지 파일들

    public Accommodation toAccommodationEntity() {
        return Accommodation.builder()
                .totalArea(totalArea)
                .roadAddress(roadAddress)
                .lotAddress(lotAddress)
                .detailAddress(detailAddress)
                .floor(floor)
                .totalFloor(totalFloor)
                .price(price)
                .transportationInfo(transportationInfo)
                .build();
    }

    public Post toPostEntity() {
        return Post.builder()
                .serviceId(SERVICE_ID)
                .userId(userId)
                .title(title)
                .content(content)
                .build();
    }

    public List<AccommodationSpace> toAccommodationSpaceEntities() {
        return accommodationSpaces.stream()
                .map(accommodationSpaceRequest -> {
                    AccommodationSpace accommodationSpace = new AccommodationSpace();
                    accommodationSpace.setAccommodationId(accommodationSpaceRequest.getAccommodationId());
                    accommodationSpace.setSpaceId(accommodationSpaceRequest.getSpaceId());
                    accommodationSpace.setCount(accommodationSpaceRequest.getCount());
                    return accommodationSpace;
                })
                .collect(Collectors.toList());
    }

    // 공간 초기화 메서드
    public void initializeSpaces(int spaceCount) {
        accommodationSpaces.clear(); // 기존 공간 초기화
        for (int i = 0; i < spaceCount; i++) {
            AccommodationSpaceRequest accommodationSpaceRequest = new AccommodationSpaceRequest();
            accommodationSpaceRequest.setSpaceId(i + 1);
            accommodationSpaceRequest.setCount(0);
            accommodationSpaces.add(accommodationSpaceRequest);
        }
    }

    public void logRequestDetails() {
        log.info("Host ID: {}", userId);
        log.info("Accommodation Name: {}", title);
        log.info("Total Area: {}", totalArea);
        log.info("Road Address: {}", roadAddress);
        log.info("Lot Address: {}", lotAddress);
        log.info("Detail Address: {}", detailAddress);
        log.info("Floor: {}", floor);
        log.info("Total Floors: {}", totalFloor);
        log.info("Price: {}", price);
        log.info("Details: {}", content);
        log.info("Transportation Info: {}", transportationInfo);
        log.info("Selected Amenities IDs: {}", amenityIds);
        log.info("Number of uploaded images: {}", (images != null ? images.size() : 0));

        // AccommodationSpaces 로그 추가
        if (accommodationSpaces != null && !accommodationSpaces.isEmpty()) {
            for (AccommodationSpaceRequest space : accommodationSpaces) {
                log.info("Accommodation Space - Space ID: {}, Count: {}", space.getSpaceId(), space.getCount());
            }
        } else {
            log.info("No accommodation spaces provided.");
        }
    }
}
