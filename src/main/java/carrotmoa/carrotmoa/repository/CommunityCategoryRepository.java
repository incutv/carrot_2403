package carrotmoa.carrotmoa.repository;

import carrotmoa.carrotmoa.entity.CommunityCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommunityCategoryRepository extends JpaRepository<CommunityCategory, Long> {
    List<CommunityCategory> findByParentIdIsNotNull();

}
