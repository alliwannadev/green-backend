package alliwannadev.shop.domain.user.support;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class TestUserDbUtil {

    private final TestUserRepository testUserRepository;

    public void deleteAll() {
        testUserRepository.deleteAll();
    }
}
