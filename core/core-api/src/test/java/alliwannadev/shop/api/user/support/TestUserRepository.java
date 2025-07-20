package alliwannadev.shop.api.user.support;

import alliwannadev.shop.core.jpa.user.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TestUserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findOneByEmail(String email);

    Boolean existsByEmail(String email);
}
