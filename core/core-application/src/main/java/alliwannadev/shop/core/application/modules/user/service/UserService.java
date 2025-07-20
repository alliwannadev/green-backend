package alliwannadev.shop.core.application.modules.user.service;

import alliwannadev.shop.core.domain.common.constant.UserSearchType;
import alliwannadev.shop.core.jpa.user.domain.User;
import alliwannadev.shop.core.jpa.user.repository.UserQueryRepository;
import alliwannadev.shop.core.jpa.user.repository.UserRepository;
import alliwannadev.shop.core.application.modules.user.service.dto.GetUserResult;
import alliwannadev.shop.support.error.BusinessException;
import alliwannadev.shop.support.error.ErrorCode;
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
    public Optional<GetUserResult> getOneByEmail(String email) {
        return userRepository.findOneByEmail(email).map(GetUserResult::from);
    }

    @Transactional(readOnly = true)
    public GetUserResult getOneByUserId(Long userId) {
        return userRepository
                .findById(userId)
                .map(GetUserResult::from)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
    }

    @Transactional
    public GetUserResult saveIfNotExists(User user) {
        return getOneByEmail(user.getEmail())
                .orElseGet(() -> GetUserResult.from(userRepository.save(user)));
    }
}
