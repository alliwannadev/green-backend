package alliwannadev.shop.core.jpa.coupon.model;

import alliwannadev.shop.core.domain.common.constant.CouponStatus;
import alliwannadev.shop.support.snowflake.GeneratedSnowflake;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class CouponEntity {

    @Id @GeneratedSnowflake
    private Long couponId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_policy_id")
    private CouponPolicyEntity couponPolicy;

    private Long userId;

    private Long orderId;

    private String couponCode;

    @Enumerated(EnumType.STRING)
    private CouponStatus couponStatus;

    private LocalDateTime usedAt;

    private LocalDateTime createdAt;

    @Builder(access = AccessLevel.PRIVATE)
    public CouponEntity(
            CouponPolicyEntity couponPolicy,
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

    public static CouponEntity of(
            CouponPolicyEntity couponPolicy,
            Long userId,
            String couponCode,
            CouponStatus couponStatus,
            LocalDateTime usedAt,
            LocalDateTime createdAt
    ) {
        return CouponEntity.builder()
                .couponPolicy(couponPolicy)
                .userId(userId)
                .couponCode(couponCode)
                .couponStatus(couponStatus)
                .usedAt(usedAt)
                .createdAt(createdAt)
                .build();
    }
}

