package carrotmoa.carrotmoa.repository;

import carrotmoa.carrotmoa.entity.Accommodation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccommodationRepository extends JpaRepository<Accommodation, Long> {
    @Query("SELECT a.id, p.title, a.roadAddress, a.price, MIN(i.imageUrl) AS imageUrl, " +
            "MAX(CASE WHEN s.spaceId = 1 THEN s.count ELSE 0 END) AS roomCount, " +  // 방 개수
            "MAX(CASE WHEN s.spaceId = 2 THEN s.count ELSE 0 END) AS bathroomCount, " +  // 화장실 개수
            "MAX(CASE WHEN s.spaceId = 3 THEN s.count ELSE 0 END) AS livingRoomCount, " + // 거실 개수
            "MAX(CASE WHEN s.spaceId = 4 THEN s.count ELSE 0 END) AS kitchenCount " +  // 주방 개수
            "FROM Accommodation a " +
            "JOIN Post p ON p.id = a.postId " +
            "LEFT JOIN AccommodationImage i ON a.id = i.accommodationId " +
            "LEFT JOIN AccommodationSpace s ON a.id = s.accommodationId " +
            "WHERE a.lotAddress LIKE %:keyword% " +
            "GROUP BY a.id, p.title, a.roadAddress, a.price")
    List<Object[]> searchAccommodationByKeyword(@Param("keyword") String keyword);

//    @Query(value = "SELECT a.id, a.userId, p.title, a.totalArea, " +
//            "a.roadAddress, a.lotAddress, a.detailAddress, a.floor, a.totalFloor, " +
//            "a.price, p.content, a.transportaionInfo, " +
//            "GROUP_CONCAT(DISTINCT ai.imageUrl) AS imageUrls, " +
//            "GROUP_CONCAT(DISTINCT am.amenityId) AS amenities, " +
//            "MAX(CASE WHEN s.spaceId = 1 THEN s.count ELSE 0 END) AS roomCount, " +
//            "MAX(CASE WHEN s.spaceId = 2 THEN s.count ELSE 0 END) AS bathroom, " +
//            "MAX(CASE WHEN s.spaceId = 3 THEN s.count ELSE 0 END) As livingRoomCount, " +
//            "MAX(CASE WHEN s.spaceId = 4 THEN s.count ELSE 0 END) As kitchenCount " +
//            "FROM Accommodation a " +
//            "LEFT JOIN AccommodationImage ai ON a.id = ai.accommodationId " +
//            "LEFT JOIN AccommodationSpace s ON a.id = s.accommodationId " +
//            "LEFT JOIN AccommodationAmenity am ON a.id = am.accommodationId " +
//            "LEFT JOIN Post p ON p.id = a.postId " +
//            "WHERE a.id = :accommodationId " +
//            "GROUP BY a.id, a.userId, a.name, a.totalArea, a.roadAddress, a.lotAddress, " +
//            "a.detailAddress, a.floor, a.totalFloor, a.price, a.detail, a.transportationInfo", nativeQuery = true)
//    List<Object[]> findAccommodationDetailsById(@Param("accommodationId") Long accommodationId);

    @Query("SELECT a.id, a.lotAddress, a.detailAddress, MIN(i.imageUrl) AS imageUrl, p.title " +
            "FROM Accommodation a " +
            "JOIN Post p ON p.id = a.postId " +
            "LEFT JOIN AccommodationImage i ON a.id = i.accommodationId " +
            "WHERE a.id = :id")
    Object[] getReservation(@Param("id") Long id);
}