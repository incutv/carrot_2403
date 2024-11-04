package carrotmoa.carrotmoa.entity;

import carrotmoa.carrotmoa.enums.ProductStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "product")
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product extends BaseEntity {

    @Column(name = "post_id")
    private Long postId;

    @Column(name = "price")
    private Integer price;

    @Column(name = "is_price_offer")
    private boolean isPriceOffer;

    @Column(name = "is_free")
    private boolean isFree;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ProductStatus status;
}
