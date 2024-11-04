package carrotmoa.carrotmoa.model.response;

import carrotmoa.carrotmoa.entity.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class CommunityPostListResponse {

    private Long communityPostId;
    private String categoryName;
    private String region3DepthName;
    private String title;
    private String content;
    private String imageUrl;
    private int commentCount;
    private LocalDateTime createdAt;

    public CommunityPostListResponse(Post post, CommunityPost communityPost, CommunityCategory communityCategory, UserAddress userAddress,
                                     PostImage postImage, int commentCount) {
        this.communityPostId = communityPost.getId();
        this.categoryName = communityCategory.getName();
        this.region3DepthName = userAddress.getRegion3DepthName();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.imageUrl = (postImage != null) ? postImage.getImageUrl() : null;
        this.commentCount = commentCount;
        this.createdAt = post.getCreatedAt();
    }
}
