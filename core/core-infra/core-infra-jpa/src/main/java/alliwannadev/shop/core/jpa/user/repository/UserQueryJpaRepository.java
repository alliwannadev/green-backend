package alliwannadev.shop.core.jpa.user.repository;

import alliwannadev.shop.core.domain.common.constant.UserSearchType;
import alliwannadev.shop.core.jpa.user.repository.dto.GetUserDto;
import alliwannadev.shop.core.jpa.user.repository.dto.QGetUserDto;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

import static alliwannadev.shop.core.jpa.user.model.QUserEntity.userEntity;

@RequiredArgsConstructor
@Repository
public class UserQueryJpaRepository {

    private final JPAQueryFactory primaryQueryFactory;

    @Transactional(readOnly = true)
    public Page<GetUserDto> findAll(
            UserSearchType searchType,
            String keyword,
            Pageable pageable
    ) {
        List<Long> userIds =
                primaryQueryFactory
                        .select(userEntity.userId)
                        .from(userEntity)
                        .where(
                                emailStartsWith(searchType, keyword),
                                phoneStartsWith(searchType, keyword)
                        )
                        .orderBy(userEntity.userId.desc())
                        .offset(pageable.getOffset())
                        .limit(pageable.getPageSize())
                        .fetch();

        if (CollectionUtils.isEmpty(userIds)) {
            return Page.empty(pageable);
        }

        List<GetUserDto> content =
                primaryQueryFactory
                        .select(
                                new QGetUserDto(
                                        userEntity.userId,
                                        userEntity.email,
                                        userEntity.password,
                                        userEntity.name,
                                        userEntity.phone,
                                        userEntity.imageUrl,
                                        userEntity.roles
                                )
                        )
                        .from(userEntity)
                        .where(userEntity.userId.in(userIds))
                        .orderBy(userEntity.userId.desc())
                        .fetch();

        Long totalCount =
                primaryQueryFactory
                        .select(userEntity.userId.countDistinct())
                        .from(userEntity)
                        .where(
                                emailStartsWith(searchType, keyword),
                                phoneStartsWith(searchType, keyword)
                        )
                        .fetchOne();

        return PageableExecutionUtils.getPage(
                content,
                pageable,
                () -> ObjectUtils.isEmpty(totalCount) ? 0 : totalCount
        );
    }

    private BooleanExpression emailStartsWith(
            UserSearchType searchType,
            String keyword
    ) {
        if (searchType == null || StringUtils.isBlank(keyword)) {
            return null;
        }

        return searchType != UserSearchType.EMAIL ? null : userEntity.email.startsWith(keyword);
    }

    private BooleanExpression phoneStartsWith(
            UserSearchType searchType,
            String keyword
    ) {
        if (searchType == null || StringUtils.isBlank(keyword)) {
            return null;
        }

        return searchType != UserSearchType.PHONE ? null : userEntity.email.startsWith(keyword);
    }
}
