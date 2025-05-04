package alliwannadev.shop.domain.user.service;

import alliwannadev.shop.common.error.BusinessException;
import alliwannadev.shop.common.error.ErrorCode;
import alliwannadev.shop.domain.user.domain.User;
import alliwannadev.shop.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public Optional<User> getOneByEmail(String email) {
        return userRepository.findOneByEmail(
                email
        );
    }

    @Transactional(readOnly = true)
    public User getOneByUserId(Long userId) {
        return userRepository
                .findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
    }

    @Transactional
    public User saveIfNotExists(User user) {
        return getOneByEmail(user.getEmail())
                .orElseGet(() -> userRepository.save(user));
    }
}
