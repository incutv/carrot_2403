package carrotmoa.carrotmoa.model.request;

import carrotmoa.carrotmoa.entity.PostImage;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
@AllArgsConstructor
public class SavePostImageRequest {
    private Long postId;
    private String imageUrl;

    public PostImage toPostImageEntity() {
        return PostImage.builder()
                .postId(postId)
                .imageUrl(imageUrl)
                .build();
    }
}
