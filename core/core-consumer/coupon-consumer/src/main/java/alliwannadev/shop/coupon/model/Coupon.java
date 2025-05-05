package alliwannadev.shop.coupon.model;

import alliwannadev.shop.coupon.constant.CouponStatus;
import alliwannadev.shop.supports.snowflake.GeneratedSnowflake;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Coupon {

    @Id @GeneratedSnowflake
    private Long couponId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_policy_id")
    private CouponPolicy couponPolicy;

    private Long userId;

    private Long orderId;

    private String couponCode;

    @Enumerated(EnumType.STRING)
    private CouponStatus couponStatus;

    private LocalDateTime usedAt;

    private LocalDateTime createdAt;

    @Builder(access = AccessLevel.PRIVATE)
    public Coupon(
            CouponPolicy couponPolicy,
            Long userId,
            Long orderId,
            String couponCode,
            CouponStatus couponStatus,
            LocalDateTime usedAt,
            LocalDateTime createdAt
    ) {
        this.couponPolicy = couponPolicy;
        this.userId = userId;
        this.orderId = orderId;
        this.couponCode = couponCode;
        this.couponStatus = couponStatus;
        this.usedAt = usedAt;
        this.createdAt = createdAt;
    }

    public static Coupon of(
            CouponPolicy couponPolicy,
            Long userId,
            String couponCode,
            CouponStatus couponStatus,
            LocalDateTime usedAt,
            LocalDateTime createdAt
    ) {
        return Coupon.builder()
                .couponPolicy(couponPolicy)
                .userId(userId)
                .couponCode(couponCode)
                .couponStatus(couponStatus)
                .usedAt(usedAt)
                .createdAt(createdAt)
                .build();
    }
}

