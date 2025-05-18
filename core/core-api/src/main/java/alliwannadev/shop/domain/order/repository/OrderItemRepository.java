package alliwannadev.shop.domain.order.repository;

import alliwannadev.shop.domain.order.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    @Query(
        """
        select  oi
        from    OrderItem oi
        where   oi.order.orderId = :orderId
        """
    )
    List<OrderItem> findAllByOrderId(Long orderId);
}
