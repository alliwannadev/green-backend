package alliwannadev.shop.core.domain.modules.order.model;

import alliwannadev.shop.core.domain.common.model.BaseTimeEntity;
import alliwannadev.shop.support.snowflake.GeneratedSnowflake;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class OrderItem extends BaseTimeEntity {

    @Id @GeneratedSnowflake
    private Long orderItemId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORDER_ID")
    private Order order;

    private Long productId;

    private Long productOptionCombinationId;

    private Long price;

    private Long discountedPrice;

    private Long paymentPrice;

    private Long quantity;

    private Long amount;

    private Long discountedAmount;

    private Long paymentAmount;

    @Builder(access = AccessLevel.PRIVATE)
    public OrderItem(
            Long productId,
            Long productOptionCombinationId,
            Long price,
            Long discountedPrice,
            Long paymentPrice,
            Long quantity,
            Long amount,
            Long discountedAmount,
            Long paymentAmount
    ) {
        this.productId = productId;
        this.productOptionCombinationId = productOptionCombinationId;
        this.price = price;
        this.discountedPrice = discountedPrice;
        this.paymentPrice = paymentPrice;
        this.quantity = quantity;
        this.amount = amount;
        this.discountedAmount = discountedAmount;
        this.paymentAmount = paymentAmount;
    }

    public static OrderItem of(
            Order order,
            Long productId,
            Long productOptionCombinationId,
            Long price,
            Long discountedPrice,
            Long paymentPrice,
            Long quantity,
            Long amount,
            Long discountedAmount,
            Long paymentAmount
    ) {
        OrderItem orderItem = OrderItem
                .builder()
                .productId(productId)
                .productOptionCombinationId(productOptionCombinationId)
                .price(price)
                .discountedPrice(discountedPrice)
                .paymentPrice(paymentPrice)
                .quantity(quantity)
                .amount(amount)
                .discountedAmount(discountedAmount)
                .paymentAmount(paymentAmount)
                .build();

        orderItem.changeOrder(order);

        return orderItem;
    }

    public void changeOrder(Order order) {
        this.order = order;
        order.getOrderItems().add(this);
    }
}
