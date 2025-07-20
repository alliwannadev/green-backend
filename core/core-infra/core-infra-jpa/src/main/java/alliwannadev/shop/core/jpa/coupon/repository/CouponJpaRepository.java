package alliwannadev.shop.core.jpa.coupon.repository;

import alliwannadev.shop.core.jpa.coupon.model.CouponEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CouponJpaRepository extends JpaRepository<CouponEntity, Long> {

    @Query(
        """
        select  c
        from    CouponEntity c
        where   c.couponPolicy.couponPolicyId = :couponPolicyId
        and     c.userId = :userId
        """
    )
    Optional<CouponEntity> findOneBy(Long couponPolicyId, Long userId);
}
