package alliwannadev.shop.core.application.coupon.service;

import alliwannadev.shop.core.domain.common.constant.CouponStatus;
import alliwannadev.shop.core.application.coupon.service.dto.IssueCouponParam;
import alliwannadev.shop.core.jpa.coupon.model.CouponEntity;
import alliwannadev.shop.core.jpa.coupon.model.CouponPolicyEntity;
import alliwannadev.shop.core.jpa.coupon.repository.CouponPolicyJpaRepository;
import alliwannadev.shop.core.jpa.coupon.repository.CouponJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class CouponService {

    private final CouponJpaRepository couponJpaRepository;
    private final CouponPolicyJpaRepository couponPolicyJpaRepository;

    @Transactional
    public void issueCoupon(
            IssueCouponParam param
    ) {
        CouponPolicyEntity couponPolicy =
                couponPolicyJpaRepository
                        .findByCouponName(param.couponName())
                        .orElseThrow();

        if (getOneBy(
                couponPolicy.getCouponPolicyId(),
                param.userId())
                .isEmpty()
        ) {
            CouponEntity coupon = CouponEntity.of(
                    couponPolicy,
                    param.userId(),
                    generateCouponCode(),
                    CouponStatus.AVAILABLE,
                    null,
                    LocalDateTime.now()
            );
            couponJpaRepository.save(coupon);
        }
    }

    @Transactional(readOnly = true)
    public Optional<CouponEntity> getOneBy(Long couponPolicyId, Long userId) {
        return couponJpaRepository.findOneBy(couponPolicyId, userId);
    }

    private String generateCouponCode() {
        return UUID
                .randomUUID()
                .toString()
                .replace("-", "")
                .substring(0, 12)
                .toUpperCase();
    }
}
