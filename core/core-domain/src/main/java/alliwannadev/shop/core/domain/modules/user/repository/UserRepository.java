package alliwannadev.shop.core.domain.modules.user.repository;

import alliwannadev.shop.core.domain.modules.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findOneByEmail(String email);
}
