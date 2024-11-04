package carrotmoa.carrotmoa.repository;

import carrotmoa.carrotmoa.entity.*;
import carrotmoa.carrotmoa.model.response.AccommodationDetailResponse;
import carrotmoa.carrotmoa.model.response.HostManagedAccommodationResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AccommodationDetailCustomRepositoryImpl implements AccommodationDetailCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    private final QAccommodation accommodation = QAccommodation.accommodation;
    private final QAccommodationImage accommodationImage = QAccommodationImage.accommodationImage;
    private final QAccommodationAmenity accommodationAmenity = QAccommodationAmenity.accommodationAmenity;
    private final QAccommodationSpace accommodationSpace = QAccommodationSpace.accommodationSpace;
    private final QPost post = QPost.post;

    public AccommodationDetailCustomRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    public AccommodationDetailResponse getAccommodationDetailById(Long id) {
        AccommodationDetailResponse detailResponse = jpaQueryFactory
                .select(Projections.fields(AccommodationDetailResponse.class,
                        accommodation.id,
                        post.title,
                        accommodation.totalArea,
                        accommodation.roadAddress,
                        accommodation.lotAddress,
                        accommodation.detailAddress,
                        accommodation.floor,
                        accommodation.totalFloor,
                        accommodation.price,
                        post.content,
                        accommodation.transportationInfo
                ))
                .from(accommodation)
                .leftJoin(post).on(accommodation.postId.eq(post.id))
                .where(accommodation.id.eq(id))
                .fetchOne();

        // 이미지 URL 쿼리
        List<String> imageUrls = jpaQueryFactory
                .select(accommodationImage.imageUrl)
                .from(accommodationImage)
                .where(accommodationImage.accommodationId.eq(id))
                .fetch();

        List<Long> amenityIds = jpaQueryFactory
                .select(accommodationAmenity.amenityId)
                .from(accommodationAmenity)
                .where(accommodationAmenity.accommodationId.eq(id))
                .fetch();

        List<Integer> spaceCounts = jpaQueryFactory
                .select(accommodationSpace.count)
                .from(accommodationSpace)
                .where(accommodationSpace.accommodationId.eq(id))
                .fetch();

        if (detailResponse != null) {
            detailResponse.setImageUrls(imageUrls);
            detailResponse.setAmenityIds(amenityIds);
            detailResponse.setSpaceCounts(spaceCounts);
        }

        return detailResponse;
    }

    public List<HostManagedAccommodationResponse> findAccommodationsByUserId(Long userId) {
        // 숙소 목록을 가져오기 위한 쿼리
        List<HostManagedAccommodationResponse> hostManagedAccommodations = jpaQueryFactory
                .selectDistinct(Projections.fields(HostManagedAccommodationResponse.class,
                        accommodation.id,
                        post.title,
                        accommodation.lotAddress,
                        accommodation.detailAddress,
                        accommodation.price
                ))
                .from(accommodation)
                .leftJoin(post).on(accommodation.postId.eq(post.id))
                .leftJoin(accommodationImage).on(accommodationImage.accommodationId.eq(accommodation.id))
                .orderBy(accommodation.createdAt.desc())
                .where(post.userId.eq(userId)) // userId 필터링
                .fetch();

        // 각 숙소에 대해 첫 번째 이미지 URL 가져오기
        for (HostManagedAccommodationResponse accommodationResponse : hostManagedAccommodations) {
            String imageUrl = jpaQueryFactory
                    .select(accommodationImage.imageUrl)
                    .from(accommodationImage)
                    .where(accommodationImage.accommodationId.eq(accommodationResponse.getId()))
                    .limit(1) // 첫 번째 이미지만 가져옴
                    .fetchOne(); // 하나의 결과만 가져오기

            accommodationResponse.setImageUrl(imageUrl);
        }

        return hostManagedAccommodations;
    }


}
