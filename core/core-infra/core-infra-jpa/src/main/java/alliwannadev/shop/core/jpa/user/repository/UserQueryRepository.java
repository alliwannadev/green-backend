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

import static alliwannadev.shop.core.jpa.user.domain.QUser.user;

@RequiredArgsConstructor
@Repository
public class UserQueryRepository {

    private final JPAQueryFactory primaryQueryFactory;

    @Transactional(readOnly = true)
    public Page<GetUserDto> findAll(
            UserSearchType searchType,
            String keyword,
            Pageable pageable
    ) {
        List<Long> userIds =
                primaryQueryFactory
                        .select(user.userId)
                        .from(user)
                        .where(
                                emailStartsWith(searchType, keyword),
                                phoneStartsWith(searchType, keyword)
                        )
                        .orderBy(user.userId.desc())
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
                                        user.userId,
                                        user.email,
                                        user.password,
                                        user.name,
                                        user.phone,
                                        user.imageUrl,
                                        user.roles
                                )
                        )
                        .from(user)
                        .where(user.userId.in(userIds))
                        .orderBy(user.userId.desc())
                        .fetch();

        Long totalCount =
                primaryQueryFactory
                        .select(user.userId.countDistinct())
                        .from(user)
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

        return searchType != UserSearchType.EMAIL ? null : user.email.startsWith(keyword);
    }

    private BooleanExpression phoneStartsWith(
            UserSearchType searchType,
            String keyword
    ) {
        if (searchType == null || StringUtils.isBlank(keyword)) {
            return null;
        }

        return searchType != UserSearchType.PHONE ? null : user.email.startsWith(keyword);
    }
}
