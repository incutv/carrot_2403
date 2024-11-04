package carrotmoa.carrotmoa.model.response;

import carrotmoa.carrotmoa.entity.*;
import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CommunityPostDetailResponse {

    private Long id;
    private Long postId;
    private Long communityCategoryId;
    private Long userId;
    private String title;
    private String content;
    private boolean isDeleted;
    private String communityCategoryName;
    private String nickname;
    private String picUrl;
    private String region2DepthName;
    private String region3DepthName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public CommunityPostDetailResponse toCommunityPostDetailResponse(CommunityPost communityPost, Post post, CommunityCategory communityCategory,
                                                                     UserProfile userProfile, UserAddress userAddress) {
        return CommunityPostDetailResponse.builder()
                .id(communityPost.getId())
                .postId(communityPost.getPostId())
                .communityCategoryId(communityPost.getCommunityCategoryId())
                .userId(post.getUserId())
                .title(post.getTitle())
                .content(post.getContent())
                .isDeleted(post.isDeleted())
                .communityCategoryName(communityCategory.getName())
                .nickname(userProfile.getNickname())
                .picUrl(userProfile.getPicUrl())
                .region2DepthName(userAddress.getRegion2DepthName())
                .region3DepthName(userAddress.getRegion3DepthName())
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .build();
    }
}