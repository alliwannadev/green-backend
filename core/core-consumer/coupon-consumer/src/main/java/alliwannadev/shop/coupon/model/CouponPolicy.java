package alliwannadev.shop.coupon.model;

import alliwannadev.shop.coupon.constant.CouponDiscountType;
import alliwannadev.shop.supports.snowflake.GeneratedSnowflake;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class CouponPolicy {

    @Id @GeneratedSnowflake
    private Long couponPolicyId;

    private String couponName;

    private String description;

    @Enumerated(EnumType.STRING)
    private CouponDiscountType discountType;

    private Integer discountValue;

    private Integer minimumOrderAmount;

    private Integer maximumDiscountAmount;

    private Integer totalQuantity;

    private LocalDateTime startIssueDate;

    private LocalDateTime endIssueDate;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}
