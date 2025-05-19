package alliwannadev.shop.domain.user.service;

import alliwannadev.shop.common.constant.UserSearchType;
import alliwannadev.shop.common.error.BusinessException;
import alliwannadev.shop.common.error.ErrorCode;
import alliwannadev.shop.domain.user.domain.User;
import alliwannadev.shop.domain.user.repository.UserQueryRepository;
import alliwannadev.shop.domain.user.repository.UserRepository;
import alliwannadev.shop.domain.user.service.dto.GetUserResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserQueryRepository userQueryRepository;

    @Transactional(readOnly = true)
    public Page<GetUserResult> getAll(
            UserSearchType searchType,
            String keyword,
            Pageable pageable
    ) {
        return userQueryRepository
                .findAll(
                        searchType,
                        keyword,
                        pageable
                )
                .map(GetUserResult::from);
    }

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
