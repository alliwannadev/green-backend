package alliwannadev.shop.core.jpa.coupon.model;

import alliwannadev.shop.core.domain.common.constant.CouponDiscountType;
import alliwannadev.shop.support.snowflake.GeneratedSnowflake;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "COUPON_POLICY")
@Entity
public class CouponPolicyEntity {

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
