package carrotmoa.carrotmoa.repository;

import carrotmoa.carrotmoa.entity.PostImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostImageRepository extends JpaRepository<PostImage, Long> {
}
