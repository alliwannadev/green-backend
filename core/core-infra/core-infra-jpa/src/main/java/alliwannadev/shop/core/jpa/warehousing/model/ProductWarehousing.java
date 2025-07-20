package alliwannadev.shop.core.jpa.warehousing.model;

import alliwannadev.shop.core.jpa.common.model.BaseTimeEntity;
import alliwannadev.shop.core.jpa.stock.model.Stock;
import alliwannadev.shop.support.snowflake.GeneratedSnowflake;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class ProductWarehousing extends BaseTimeEntity {

    @Id @GeneratedSnowflake
    private Long warehousingId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stock_id")
    private Stock stock;

    private LocalDate warehousingDate;

    private Long productOptionCombinationId;

    private Long quantity;

    @Builder(access = AccessLevel.PRIVATE)
    private ProductWarehousing(
            Stock stock,
            LocalDate warehousingDate,
            Long productOptionCombinationId,
            Long quantity
    ) {
        this.stock = stock;
        this.warehousingDate = warehousingDate;
        this.productOptionCombinationId = productOptionCombinationId;
        this.quantity = quantity;
    }

    public static ProductWarehousing of(
            Stock stock,
            LocalDate warehousingDate,
            Long productOptionCombinationId,
            Long quantity
    ) {
        ProductWarehousing productWarehousing = ProductWarehousing
                .builder()
                .warehousingDate(warehousingDate)
                .productOptionCombinationId(productOptionCombinationId)
                .quantity(quantity)
                .build();

        productWarehousing.changeStock(stock);

        return productWarehousing;
    }

    public void changeStock(Stock stock) {
        this.stock = stock;
    }
}
