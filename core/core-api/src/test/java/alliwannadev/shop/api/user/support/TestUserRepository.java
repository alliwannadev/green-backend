package alliwannadev.shop.api.user.support;

import alliwannadev.shop.core.jpa.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TestUserRepository extends JpaRepository<User, Long> {

    Optional<User> findOneByEmail(String email);

    Boolean existsByEmail(String email);
}
