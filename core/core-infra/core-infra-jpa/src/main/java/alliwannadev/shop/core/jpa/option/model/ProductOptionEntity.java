package alliwannadev.shop.core.jpa.option.model;

import alliwannadev.shop.core.jpa.common.model.BaseTimeEntity;
import alliwannadev.shop.core.jpa.product.model.ProductEntity;
import alliwannadev.shop.support.snowflake.GeneratedSnowflake;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class ProductOptionEntity extends BaseTimeEntity {

    @Id @GeneratedSnowflake
    private Long productOptionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_ID")
    private ProductEntity product;

    private String optionCode;

    private String optionName;

    private String optionValue;

    @Builder(access = AccessLevel.PRIVATE)
    private ProductOptionEntity(
            ProductEntity product,
            String optionCode,
            String optionName,
            String optionValue
    ) {
        this.product = product;
        this.optionCode = optionCode;
        this.optionName = optionName;
        this.optionValue = optionValue;
    }

    public static ProductOptionEntity of(
            ProductEntity product,
            String optionCode,
            String optionName,
            String optionValue
    ) {
        ProductOptionEntity productOption = ProductOptionEntity
                .builder()
                .optionCode(optionCode)
                .optionName(optionName)
                .optionValue(optionValue)
                .build();

        productOption.changeProduct(product);

        return productOption;
    }

    public void changeProduct(ProductEntity product) {
        this.product = product;
    }
}
