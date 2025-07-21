package alliwannadev.shop.core.jpa.coupon.repository;

import alliwannadev.shop.core.jpa.coupon.model.CouponPolicyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CouponPolicyJpaRepository extends JpaRepository<CouponPolicyEntity, Long> {

    Optional<CouponPolicyEntity> findByCouponName(String couponName);
}
