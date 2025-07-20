package alliwannadev.shop.core.jpa.stock.model;

import alliwannadev.shop.core.jpa.common.model.BaseTimeEntity;
import alliwannadev.shop.support.error.BusinessException;
import alliwannadev.shop.support.error.ErrorCode;
import alliwannadev.shop.support.snowflake.GeneratedSnowflake;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "STOCK")
@Entity
public class StockEntity extends BaseTimeEntity {

    @Id @GeneratedSnowflake
    private Long stockId;

    private Long productOptionCombinationId;

    private Long quantity;

    @Builder(access = AccessLevel.PRIVATE)
    private StockEntity(
            Long productOptionCombinationId,
            Long quantity
    ) {
        this.productOptionCombinationId = productOptionCombinationId;
        this.quantity = quantity;
    }

    public static StockEntity of(
            Long productOptionCombinationId,
            Long quantity
    ) {
        return StockEntity
                .builder()
                .productOptionCombinationId(productOptionCombinationId)
                .quantity(quantity)
                .build();
    }

    public void increaseQuantity(Long quantity) {
        this.quantity += quantity;
    }

    public void decreaseQuantity(Long quantity) {
        if (this.quantity - quantity < 0) {
            throw new BusinessException(ErrorCode.OUT_OF_STOCK);
        }

        this.quantity -= quantity;
    }
}
