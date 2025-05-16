package alliwannadev.shop.domain.option.model;

import alliwannadev.shop.common.model.BaseTimeEntity;
import alliwannadev.shop.domain.product.model.Product;
import alliwannadev.shop.supports.snowflake.GeneratedSnowflake;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class ProductOption extends BaseTimeEntity {

    @Id @GeneratedSnowflake
    private Long productOptionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;

    private String optionCode;

    private String optionName;

    private String optionValue;

    @Builder(access = AccessLevel.PRIVATE)
    private ProductOption(
            Product product,
            String optionCode,
            String optionName,
            String optionValue
    ) {
        this.product = product;
        this.optionCode = optionCode;
        this.optionName = optionName;
        this.optionValue = optionValue;
    }

    public static ProductOption of(
            Product product,
            String optionCode,
            String optionName,
            String optionValue
    ) {
        ProductOption productOption = ProductOption
                .builder()
                .optionCode(optionCode)
                .optionName(optionName)
                .optionValue(optionValue)
                .build();

        productOption.changeProduct(product);

        return productOption;
    }

    public void changeProduct(Product product) {
        this.product = product;
    }
}
