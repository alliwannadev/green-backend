package alliwannadev.shop.domain.product.repository;

import alliwannadev.shop.common.dto.InfiniteScrollCond;
import alliwannadev.shop.domain.product.repository.dto.GetProductDto;
import alliwannadev.shop.domain.product.repository.dto.GetProductListCond;
import alliwannadev.shop.domain.product.repository.dto.QGetProductDto;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static alliwannadev.shop.domain.product.model.QProduct.product;

@RequiredArgsConstructor
@Repository
public class ProductQueryRepository {

    private final JPAQueryFactory primaryQueryFactory;

    @Transactional(readOnly = true)
    public Page<GetProductDto> findAll(
            GetProductListCond condition,
            Pageable pageable
    ) {
        List<GetProductDto> content = primaryQueryFactory
                .select(
                        new QGetProductDto(
                                product.productId,
                                product.productCode,
                                product.productName,
                                product.modelName,
                                product.originalPrice,
                                product.sellingPrice,
                                product.description,
                                product.isDisplayed
                        )
                )
                .from(product)
                .orderBy(product.productId.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long totalCount = primaryQueryFactory
                .select(product.productId.countDistinct())
                .from(product)
                .fetchOne();

        return PageableExecutionUtils.getPage(
                content,
                pageable,
                () -> ObjectUtils.isEmpty(totalCount) ? 0 : totalCount
        );
    }

    @Transactional(readOnly = true)
    public List<GetProductDto> findAllInfiniteScroll(
            String categoryPath,
            InfiniteScrollCond infiniteScrollCond
    ) {
        return primaryQueryFactory
                .select(
                        new QGetProductDto(
                                product.productId,
                                product.productCode,
                                product.productName,
                                product.modelName,
                                product.originalPrice,
                                product.sellingPrice,
                                product.description,
                                product.isDisplayed
                        )
                )
                .from(product)
                .where(
                        productIdLt(infiniteScrollCond.lastCursor())
                )
                .orderBy(product.productId.desc())
                .limit(infiniteScrollCond.getPageSizeWithOneAdded())
                .fetch();
    }

    private BooleanExpression productIdLt(Long productId) {
        return productId == null ? null : product.productId.lt(productId);
    }
}
