package carrotmoa.carrotmoa.repository;

import carrotmoa.carrotmoa.enums.ProductStatus;
import carrotmoa.carrotmoa.model.response.FleaMarketPostResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static carrotmoa.carrotmoa.entity.QPost.post;
import static carrotmoa.carrotmoa.entity.QProduct.product;

@Repository
public class FleaMarketCustomRepositoryImpl implements FleaMarketPostRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public FleaMarketCustomRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public List<FleaMarketPostResponse> findPostList() {
        return getPostList()
                .fetch();
    }

    @Override
    public Page<FleaMarketPostResponse> findPostListToPage(Pageable pageable) {
        List<FleaMarketPostResponse> results = getPostList()
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long totalCount = getTotalCount();
        return new PageImpl<>(results, pageable, totalCount);
    }

    private JPAQuery<FleaMarketPostResponse> getPostList() {
        return jpaQueryFactory
                .select(
                        Projections.constructor(FleaMarketPostResponse.class,
                                post.id,
                                post.userId,
                                post.title,
                                post.content,
                                product.id,
                                product.price,
                                product.isPriceOffer,
                                product.isFree,
                                product.status,
                                post.createdAt
                        )
                )
                .from(post)
                .leftJoin(product).on(post.id.eq(product.postId))
                .where(post.serviceId.eq(1L),
                        product.postId.isNotNull(),
                        post.isDeleted.isFalse(),
                        product.status.ne(ProductStatus.SOLD_OUT));
    }

    private Long getTotalCount() {
        Long totalCount = jpaQueryFactory
                .select(post.count())
                .from(post)
                .leftJoin(product).on(post.id.eq(product.postId))
                .where(post.serviceId.eq(1L),
                        product.postId.isNotNull(),
                        post.isDeleted.isFalse(),
                        product.status.eq(ProductStatus.SOLD_OUT))
                .fetchOne();

        return totalCount != null ? totalCount : 0L;
    }
}