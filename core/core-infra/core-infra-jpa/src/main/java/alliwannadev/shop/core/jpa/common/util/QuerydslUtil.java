package alliwannadev.shop.core.jpa.common.util;

import alliwannadev.shop.core.jpa.common.config.CustomFunctionContributor;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringPath;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class QuerydslUtil {

    public static BooleanBuilder orConditions(Predicate... predicates) {
        BooleanBuilder builder = new BooleanBuilder();
        Arrays.stream(predicates).forEach(builder::or);
        return builder;
    }

    public static BooleanExpression matchAgainst(
            final StringPath target,
            final String column
    ) {
        if (StringUtils.isBlank(column)) {
            return null;
        }

        return Expressions.numberTemplate(
                Double.class,
                "FUNCTION('MATCH_AGAINST', {0}, {1})",
                target,
                column
        ).gt(CustomFunctionContributor.MATCH_THRESHOLD);
    }
}
