package carrotmoa.carrotmoa.model.response;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * carrotmoa.carrotmoa.model.response.QHostManagedAccommodationResponse is a Querydsl Projection type for HostManagedAccommodationResponse
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QHostManagedAccommodationResponse extends ConstructorExpression<HostManagedAccommodationResponse> {

    private static final long serialVersionUID = -1541331552L;

    public QHostManagedAccommodationResponse(com.querydsl.core.types.Expression<Long> id, com.querydsl.core.types.Expression<String> title, com.querydsl.core.types.Expression<String> lotAddress, com.querydsl.core.types.Expression<String> detailAddress, com.querydsl.core.types.Expression<? extends java.math.BigDecimal> price) {
        super(HostManagedAccommodationResponse.class, new Class<?>[]{long.class, String.class, String.class, String.class, java.math.BigDecimal.class}, id, title, lotAddress, detailAddress, price);
    }

}

