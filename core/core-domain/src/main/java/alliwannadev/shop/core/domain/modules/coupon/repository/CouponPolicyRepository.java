package alliwannadev.shop.core.domain.modules.coupon.repository;

import alliwannadev.shop.core.domain.modules.coupon.model.CouponPolicy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CouponPolicyRepository extends JpaRepository<CouponPolicy, Long> {

    Optional<CouponPolicy> findByCouponName(String couponName);
}
