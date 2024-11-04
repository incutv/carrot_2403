package carrotmoa.carrotmoa.repository;

import carrotmoa.carrotmoa.entity.AccommodationImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccommodationImageRepository extends JpaRepository<AccommodationImage, Long> {

}
