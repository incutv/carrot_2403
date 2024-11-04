package carrotmoa.carrotmoa.model.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class FleaMarketPostListPageResponse {

    private List<FleaMarketPostResponse> fleaMarketPosts;
    private Long currentPage;
    private Long totalPages;
    private Long totalPosts;

    public static FleaMarketPostListPageResponse toFleaMarketPostListResponse(List<FleaMarketPostResponse> fleaMarketPosts,
                                                                              Long currentPage, Long totalPages, Long totalPosts) {

        return FleaMarketPostListPageResponse.builder()
                .fleaMarketPosts(fleaMarketPosts)
                .currentPage(currentPage)
                .totalPages(totalPages)
                .totalPosts(totalPosts)
                .build();
    }
}
