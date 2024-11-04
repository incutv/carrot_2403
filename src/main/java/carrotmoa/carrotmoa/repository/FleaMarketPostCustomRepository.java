package carrotmoa.carrotmoa.repository;

import carrotmoa.carrotmoa.model.response.FleaMarketPostResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FleaMarketPostCustomRepository {

    Page<FleaMarketPostResponse> findPostListToPage(Pageable pageable);

    List<FleaMarketPostResponse> findPostList();
}
