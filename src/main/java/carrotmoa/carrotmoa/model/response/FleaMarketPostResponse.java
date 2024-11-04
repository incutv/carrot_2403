package carrotmoa.carrotmoa.model.response;

import carrotmoa.carrotmoa.enums.ProductStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class FleaMarketPostResponse {

    private Long postId;
    private Long userId;
    private String title;
    private String content;
    private Long productId;
    private Integer price;
    private boolean isPriceOffer;
    private boolean isFree;
    private ProductStatus productStatus;
    private LocalDateTime createdAt;

    public FleaMarketPostResponse(
            Long postId,
            Long userId,
            String postTitle,
            String content,
            Long productId,
            Integer price,
            boolean isPriceOffer,
            boolean isFree,
            ProductStatus productStatus,
            LocalDateTime createdAt) {
        this.postId = postId;
        this.userId = userId;
        this.title = postTitle;
        this.content = content;
        this.productId = productId;
        this.price = price;
        this.isPriceOffer = isPriceOffer;
        this.isFree = isFree;
        this.productStatus = productStatus;
        this.createdAt = createdAt;
    }
}
