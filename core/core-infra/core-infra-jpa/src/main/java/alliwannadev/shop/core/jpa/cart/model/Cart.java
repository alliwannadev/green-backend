package alliwannadev.shop.core.jpa.cart.model;

import alliwannadev.shop.core.jpa.user.domain.User;
import alliwannadev.shop.support.snowflake.GeneratedSnowflake;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Cart {

    @Id @GeneratedSnowflake
    private Long cartId;

    @JoinColumn(name = "USER_ID")
    @OneToOne(fetch = FetchType.LAZY)
    private User user;

    private Long totalQuantity;

    private Long totalAmount;

    private Long totalDiscountedAmount;

    private Long totalPaymentAmount;

    @Builder(access = AccessLevel.PRIVATE)
    public Cart(
            User user,
            Long totalQuantity,
            Long totalAmount,
            Long totalDiscountedAmount,
            Long totalPaymentAmount
    ) {
        this.user = user;
        this.totalQuantity = totalQuantity;
        this.totalAmount = totalAmount;
        this.totalDiscountedAmount = totalDiscountedAmount;
        this.totalPaymentAmount = totalPaymentAmount;
    }

    public static Cart of(
            User user,
            Long totalQuantity,
            Long totalAmount,
            Long totalDiscountedAmount,
            Long totalPaymentAmount
    ) {
        Cart cart = Cart.builder()
                .totalQuantity(totalQuantity)
                .totalAmount(totalAmount)
                .totalDiscountedAmount(totalDiscountedAmount)
                .totalPaymentAmount(totalPaymentAmount)
                .build();

        cart.changeUser(user);

        return cart;
    }

    public void changeUser(User user) {
        this.user = user;
    }

    public void update(
            Long totalQuantity,
            Long totalAmount,
            Long totalDiscountedAmount,
            Long totalPaymentAmount
    ) {
        if (ObjectUtils.isNotEmpty(totalQuantity)) {
            this.totalQuantity = totalQuantity;
        }

        if (ObjectUtils.isNotEmpty(totalAmount)) {
            this.totalAmount = totalAmount;
        }

        if (ObjectUtils.isNotEmpty(totalDiscountedAmount)) {
            this.totalDiscountedAmount = totalDiscountedAmount;
        }

        if (ObjectUtils.isNotEmpty(totalPaymentAmount)) {
            this.totalPaymentAmount = totalPaymentAmount;
        }
    }
}
