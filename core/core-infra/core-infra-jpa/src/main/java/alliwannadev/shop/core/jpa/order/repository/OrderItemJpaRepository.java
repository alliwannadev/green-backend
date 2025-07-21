package alliwannadev.shop.core.jpa.order.repository;

import alliwannadev.shop.core.jpa.order.model.OrderItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderItemJpaRepository extends JpaRepository<OrderItemEntity, Long> {

    @Query(
        """
        select  oi
        from    OrderItemEntity oi
        where   oi.order.orderId = :orderId
        """
    )
    List<OrderItemEntity> findAllByOrderId(Long orderId);
}
