package alliwannadev.shop.domain.order.repository;

import alliwannadev.shop.domain.order.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
