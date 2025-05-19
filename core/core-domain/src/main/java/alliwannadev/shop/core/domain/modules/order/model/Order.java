package alliwannadev.shop.core.domain.modules.order.model;

import alliwannadev.shop.core.domain.common.constant.OrderStatus;
import alliwannadev.shop.core.domain.common.model.BaseTimeEntity;
import alliwannadev.shop.core.domain.common.util.OrderStatusConverter;
import alliwannadev.shop.supports.snowflake.GeneratedSnowflake;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Table(name = "ORDERS")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Order extends BaseTimeEntity {

    @Id @GeneratedSnowflake
    private Long orderId;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems = new ArrayList<>();

    private Long userId;

    private String orderNo;

    @Convert(converter = OrderStatusConverter.class)
    private OrderStatus orderStatus;

    private String receiverName;

    private String receiverPhoneNumber;

    private String receiverAddr;

    private String receiverPostalCode;

    private Long totalQuantity;

    private Long totalAmount;

    private Long totalDiscountedAmount;

    private Long totalPaymentAmount;

    @Builder(access = AccessLevel.PRIVATE)
    public Order(
            Long userId,
            String orderNo,
            OrderStatus orderStatus,
            String receiverName,
            String receiverPhoneNumber,
            String receiverAddr,
            String receiverPostalCode,
            Long totalQuantity,
            Long totalAmount,
            Long totalDiscountedAmount,
            Long totalPaymentAmount
    ) {
        this.userId = userId;
        this.orderNo = orderNo;
        this.orderStatus = orderStatus;
        this.receiverName = receiverName;
        this.receiverPhoneNumber = receiverPhoneNumber;
        this.receiverAddr = receiverAddr;
        this.receiverPostalCode = receiverPostalCode;
        this.totalQuantity = totalQuantity;
        this.totalAmount = totalAmount;
        this.totalDiscountedAmount = totalDiscountedAmount;
        this.totalPaymentAmount = totalPaymentAmount;
    }

    public static Order of(
            Long userId,
            String orderNo,
            OrderStatus orderStatus,
            String receiverName,
            String receiverPhoneNumber,
            String receiverAddr,
            String receiverPostalCode,
            Long totalQuantity,
            Long totalAmount,
            Long totalDiscountedAmount,
            Long totalPaymentAmount
    ) {
        return Order
                .builder()
                .userId(userId)
                .orderNo(orderNo)
                .orderStatus(orderStatus)
                .receiverName(receiverName)
                .receiverPhoneNumber(receiverPhoneNumber)
                .receiverAddr(receiverAddr)
                .receiverPostalCode(receiverPostalCode)
                .totalQuantity(totalQuantity)
                .totalAmount(totalAmount)
                .totalDiscountedAmount(totalDiscountedAmount)
                .totalPaymentAmount(totalPaymentAmount)
                .build();
    }
}
