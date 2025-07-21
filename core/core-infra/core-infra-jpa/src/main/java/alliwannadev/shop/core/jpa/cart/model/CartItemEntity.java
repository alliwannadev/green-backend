package alliwannadev.shop.core.jpa.cart.model;

import alliwannadev.shop.support.snowflake.GeneratedSnowflake;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "CART_ITEM")
@Entity
public class CartItemEntity {

    @Id @GeneratedSnowflake
    private Long cartItemId;

    @JoinColumn(name = "CART_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private CartEntity cart;

    private Long productId;

    private String productName;

    private Long productOptionCombinationId;

    private String selectedOptions;

    private String thumbnailUrl;

    private Long price;

    private Long quantity;

    private Long amount;

    private Long discountedAmount;

    private Long paymentAmount;

    @Builder(access = AccessLevel.PRIVATE)
    public CartItemEntity(
            CartEntity cart,
            Long productId,
            String productName,
            Long productOptionCombinationId,
            String selectedOptions,
            String thumbnailUrl,
            Long quantity,
            Long price,
            Long amount,
            Long discountedAmount,
            Long paymentAmount
    ) {
        this.cart = cart;
        this.productId = productId;
        this.productName = productName;
        this.productOptionCombinationId = productOptionCombinationId;
        this.selectedOptions = selectedOptions;
        this.thumbnailUrl = thumbnailUrl;
        this.quantity = quantity;
        this.price = price;
        this.amount = amount;
        this.discountedAmount = discountedAmount;
        this.paymentAmount = paymentAmount;
    }

    public static CartItemEntity of(
            CartEntity cart,
            Long productId,
            String productName,
            Long productOptionCombinationId,
            String selectedOptions,
            String thumbnailUrl,
            Long price,
            Long quantity,
            Long amount,
            Long discountedAmount
    ) {
        CartItemEntity cartItemEntity = CartItemEntity.builder()
                .productId(productId)
                .productName(productName)
                .productOptionCombinationId(productOptionCombinationId)
                .selectedOptions(selectedOptions)
                .thumbnailUrl(thumbnailUrl)
                .price(price)
                .quantity(quantity)
                .amount(amount)
                .discountedAmount(discountedAmount)
                .paymentAmount(amount - discountedAmount)
                .build();

        cartItemEntity.changeCart(cart);
        return cartItemEntity;
    }

    public void changeCart(CartEntity cart) {
        this.cart = cart;
    }

    public void update(
            Long price,
            Long quantity,
            Long amount,
            Long discountedAmount
    ) {
        this.price = price;
        this.quantity = quantity;
        this.amount = amount;
        this.discountedAmount = discountedAmount;
        this.paymentAmount = amount - discountedAmount;
    }

    public void updateQuantity(Long quantity) {
        this.quantity = this.quantity + quantity;
        this.amount = this.price * this.quantity;
        this.paymentAmount = this.price * this.quantity - discountedAmount;
    }
}
