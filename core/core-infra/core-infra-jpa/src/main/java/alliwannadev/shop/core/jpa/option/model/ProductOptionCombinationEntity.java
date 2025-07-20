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
public class ProductOptionCombinationEntity extends BaseTimeEntity {

    @Id @GeneratedSnowflake
    private Long productOptionCombinationId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_ID")
    private ProductEntity product;

    private String selectedOptions;

    private String combinationCode;

    private String uniqueCombinationCode;

    private String optionManagementCode;

    @Builder(access = AccessLevel.PRIVATE)
    private ProductOptionCombinationEntity(
            ProductEntity product,
            String selectedOptions,
            String combinationCode,
            String uniqueCombinationCode,
            String optionManagementCode
    ) {
        this.product = product;
        this.selectedOptions = selectedOptions;
        this.combinationCode = combinationCode;
        this.uniqueCombinationCode = uniqueCombinationCode;
        this.optionManagementCode = optionManagementCode;
    }

    public static ProductOptionCombinationEntity of(
            ProductEntity product,
            String selectedOptions,
            String combinationCode,
            String uniqueCombinationCode,
            String optionManagementCode
    ) {
        ProductOptionCombinationEntity productOptionCombination = ProductOptionCombinationEntity
                .builder()
                .selectedOptions(selectedOptions)
                .combinationCode(combinationCode)
                .uniqueCombinationCode(uniqueCombinationCode)
                .optionManagementCode(optionManagementCode)
                .build();

        productOptionCombination.changeProduct(product);

        return productOptionCombination;
    }

    public void changeProduct(ProductEntity product) {
        this.product = product;
    }
}
