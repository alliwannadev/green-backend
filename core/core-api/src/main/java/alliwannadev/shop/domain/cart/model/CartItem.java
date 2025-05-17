package alliwannadev.shop.domain.cart.model;

import alliwannadev.shop.supports.snowflake.GeneratedSnowflake;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class CartItem {

    @Id @GeneratedSnowflake
    private Long cartItemId;

    @JoinColumn(name = "CART_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Cart cart;

    private Long productId;

    private String productName;

    private String selectedOptions;

    private String thumbnailUrl;

    private Long price;

    private Long quantity;

    private Long amount;

    private Long discountedAmount;

    private Long paymentAmount;

    @Builder(access = AccessLevel.PRIVATE)
    public CartItem(
            Cart cart,
            Long productId,
            String productName,
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
        this.selectedOptions = selectedOptions;
        this.thumbnailUrl = thumbnailUrl;
        this.quantity = quantity;
        this.price = price;
        this.amount = amount;
        this.discountedAmount = discountedAmount;
        this.paymentAmount = paymentAmount;
    }

    public static CartItem of(
            Cart cart,
            Long productId,
            String productName,
            String selectedOptions,
            String thumbnailUrl,
            Long price,
            Long quantity,
            Long amount,
            Long discountedAmount
    ) {
        CartItem cartItem = CartItem.builder()
                .productId(productId)
                .productName(productName)
                .selectedOptions(selectedOptions)
                .thumbnailUrl(thumbnailUrl)
                .price(price)
                .quantity(quantity)
                .amount(amount)
                .discountedAmount(discountedAmount)
                .paymentAmount(amount - discountedAmount)
                .build();

        cartItem.changeCart(cart);
        return cartItem;
    }

    public void changeCart(Cart cart) {
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
