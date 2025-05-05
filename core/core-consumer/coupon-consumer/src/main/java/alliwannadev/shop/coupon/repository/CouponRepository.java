package alliwannadev.shop.coupon.repository;

import alliwannadev.shop.coupon.model.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CouponRepository extends JpaRepository<Coupon, Long> {

    @Query(
        """
        select  c
        from    Coupon c
        where   c.couponPolicy.couponPolicyId = :couponPolicyId
        and     c.userId = :userId
        """
    )
    Optional<Coupon> findOneBy(Long couponPolicyId, Long userId);
}
