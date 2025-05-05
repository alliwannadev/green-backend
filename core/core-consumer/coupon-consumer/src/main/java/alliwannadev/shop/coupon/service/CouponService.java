package alliwannadev.shop.coupon.service;

import alliwannadev.shop.coupon.constant.CouponStatus;
import alliwannadev.shop.coupon.model.Coupon;
import alliwannadev.shop.coupon.model.CouponPolicy;
import alliwannadev.shop.coupon.repository.CouponPolicyRepository;
import alliwannadev.shop.coupon.repository.CouponRepository;
import alliwannadev.shop.coupon.service.dto.IssueCouponParam;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class CouponService {

    private final CouponRepository couponRepository;
    private final CouponPolicyRepository couponPolicyRepository;

    @Transactional
    public void issueCoupon(
            IssueCouponParam param
    ) {
        CouponPolicy couponPolicy =
                couponPolicyRepository
                        .findByCouponName(param.couponName())
                        .orElseThrow();

        if (getOneBy(
                couponPolicy.getCouponPolicyId(),
                param.userId())
                .isEmpty()
        ) {
            Coupon coupon = Coupon.of(
                    couponPolicy,
                    param.userId(),
                    generateCouponCode(),
                    CouponStatus.AVAILABLE,
                    null,
                    LocalDateTime.now()
            );
            couponRepository.save(coupon);
        }
    }

    @Transactional(readOnly = true)
    public Optional<Coupon> getOneBy(Long couponPolicyId, Long userId) {
        return couponRepository.findOneBy(couponPolicyId, userId);
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
