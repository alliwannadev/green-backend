package alliwannadev.shop.domain.user.repository;

import alliwannadev.shop.common.util.QuerydslUtil;
import alliwannadev.shop.domain.user.repository.dto.GetUserDto;
import alliwannadev.shop.domain.user.repository.dto.QGetUserDto;
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

import java.util.List;

import static alliwannadev.shop.domain.user.domain.QUser.user;

@RequiredArgsConstructor
@Repository
public class UserQueryRepository {

    private final JPAQueryFactory primaryQueryFactory;

    @Transactional(readOnly = true)
    public Page<GetUserDto> findAll(
            String keyword,
            Pageable pageable
    ) {
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
                        .where(
                                QuerydslUtil.orConditions(
                                        emailStartsWith(keyword),
                                        phoneStartsWith(keyword)
                                )
                        )
                        .orderBy(user.userId.desc())
                        .offset(pageable.getOffset())
                        .limit(pageable.getPageSize())
                        .fetch();

        Long totalCount =
                primaryQueryFactory
                        .select(user.userId.countDistinct())
                        .from(user)
                        .where(
                                QuerydslUtil.orConditions(
                                        emailStartsWith(keyword),
                                        phoneStartsWith(keyword)
                                )
                        )
                        .fetchOne();

        return PageableExecutionUtils.getPage(
                content,
                pageable,
                () -> ObjectUtils.isEmpty(totalCount) ? 0 : totalCount
        );
    }

    private BooleanExpression emailStartsWith(String keyword) {
        return StringUtils.isBlank(keyword) ? null : user.email.startsWith(keyword);
    }

    private BooleanExpression phoneStartsWith(String keyword) {
        return StringUtils.isBlank(keyword) ? null : user.phone.startsWith(keyword);
    }
}
