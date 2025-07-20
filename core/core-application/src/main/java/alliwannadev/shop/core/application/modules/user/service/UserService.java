package alliwannadev.shop.core.application.modules.user.service;

import alliwannadev.shop.core.domain.common.constant.UserSearchType;
import alliwannadev.shop.core.jpa.user.model.UserEntity;
import alliwannadev.shop.core.jpa.user.repository.UserQueryJpaRepository;
import alliwannadev.shop.core.jpa.user.repository.UserJpaRepository;
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

    private final UserJpaRepository userJpaRepository;
    private final UserQueryJpaRepository userQueryJpaRepository;

    @Transactional(readOnly = true)
    public Page<GetUserResult> getAll(
            UserSearchType searchType,
            String keyword,
            Pageable pageable
    ) {
        return userQueryJpaRepository
                .findAll(
                        searchType,
                        keyword,
                        pageable
                )
                .map(GetUserResult::from);
    }

    @Transactional(readOnly = true)
    public Optional<GetUserResult> getOneByEmail(String email) {
        return userJpaRepository.findOneByEmail(email).map(GetUserResult::from);
    }

    @Transactional(readOnly = true)
    public GetUserResult getOneByUserId(Long userId) {
        return userJpaRepository
                .findById(userId)
                .map(GetUserResult::from)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
    }

    @Transactional
    public GetUserResult saveIfNotExists(UserEntity userEntity) {
        return getOneByEmail(userEntity.getEmail())
                .orElseGet(() -> GetUserResult.from(userJpaRepository.save(userEntity)));
    }
}
