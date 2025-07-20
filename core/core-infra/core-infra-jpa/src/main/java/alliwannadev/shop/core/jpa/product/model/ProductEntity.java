package alliwannadev.shop.core.jpa.product.model;

import alliwannadev.shop.core.jpa.common.model.BaseTimeEntity;
import alliwannadev.shop.support.snowflake.GeneratedSnowflake;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "PRODUCT")
@Entity
public class ProductEntity extends BaseTimeEntity {

    @Id @GeneratedSnowflake
    private Long productId;

    private String productCode;

    private String productName;

    private String modelName;

    private Long originalPrice;

    private Long sellingPrice;

    private String description;

    private Boolean isDisplayed;

    @Builder(access = AccessLevel.PRIVATE)
    private ProductEntity(
            String productCode,
            String productName,
            String modelName,
            Long originalPrice,
            Long sellingPrice,
            String description,
            Boolean isDisplayed
    ) {
        this.productCode = productCode;
        this.productName = productName;
        this.modelName = modelName;
        this.originalPrice = originalPrice;
        this.sellingPrice = sellingPrice;
        this.description = description;
        this.isDisplayed = isDisplayed;
    }

    public static ProductEntity of(
            String productCode,
            String productName,
            String modelName,
            Long originalPrice,
            Long sellingPrice,
            String description,
            Boolean isDisplayed
    ) {
        return ProductEntity
                .builder()
                .productCode(productCode)
                .productName(productName)
                .modelName(modelName)
                .originalPrice(originalPrice)
                .sellingPrice(sellingPrice)
                .description(description)
                .isDisplayed(isDisplayed)
                .build();
    }
}
