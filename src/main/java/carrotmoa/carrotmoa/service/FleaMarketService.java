package carrotmoa.carrotmoa.service;

import carrotmoa.carrotmoa.entity.Post;
import carrotmoa.carrotmoa.model.request.SaveFleaMarketPostRequest;
import carrotmoa.carrotmoa.model.response.FleaMarketPostListPageResponse;
import carrotmoa.carrotmoa.model.response.FleaMarketPostResponse;
import carrotmoa.carrotmoa.repository.FleaMarketPostRepository;
import carrotmoa.carrotmoa.repository.PostRepository;
import carrotmoa.carrotmoa.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FleaMarketService {

    private static final int PAGE_SIZE = 9;
    private final PostRepository postRepository;
    private final ProductRepository productRepository;
    private final FleaMarketPostRepository fleaMarketPostRepository;

    @Transactional(readOnly = true)
    public Long savePost(SaveFleaMarketPostRequest saveFleaMarketPostRequest) {
        validatePost(saveFleaMarketPostRequest);

        Post post = postRepository.save(saveFleaMarketPostRequest.toPostEntity());
        productRepository.save(saveFleaMarketPostRequest.toProductEntity(post.getId()));
        return post.getId();
    }

    private void validatePost(SaveFleaMarketPostRequest saveFleaMarketPostRequest) {
        //TODO : 검증 추가
    }

    @Transactional(readOnly = true)
    public FleaMarketPostListPageResponse getPostListPage(Long page) {
        Pageable pageable = PageRequest.of((int) (page - 1), PAGE_SIZE);
        Page<FleaMarketPostResponse> fleaMarketPosts = fleaMarketPostRepository.findPostListToPage(pageable);

        return FleaMarketPostListPageResponse.toFleaMarketPostListResponse(fleaMarketPosts.getContent(), page,
                (long) fleaMarketPosts.getTotalPages(), fleaMarketPosts.getTotalElements());
    }

    @Transactional(readOnly = true)
    public List<FleaMarketPostResponse> getPostList() {
        return fleaMarketPostRepository.findPostList();
    }

    @Transactional(readOnly = true)
    public FleaMarketPostResponse getPost(Long page) {
        return null;
    }
}
