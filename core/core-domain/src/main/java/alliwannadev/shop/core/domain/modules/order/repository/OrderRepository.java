package alliwannadev.shop.core.domain.modules.order.repository;

import alliwannadev.shop.core.domain.modules.order.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    Optional<Order> findByOrderNo(String orderNo);
}
