package alliwannadev.shop.core.jpa.common.util;

import alliwannadev.shop.core.domain.common.constant.Role;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Converter
public class RolesConverter implements AttributeConverter<Set<Role>, String> {

    private static final String ROLE_DELIMITER = ",";

    @Override
    public String convertToDatabaseColumn(Set<Role> roles) {
        if (CollectionUtils.isEmpty(roles)) {
            return ROLE_DELIMITER;
        }

        return roles
                .stream()
                .map(Role::name)
                .collect(Collectors.joining(ROLE_DELIMITER));
    }

    @Override
    public Set<Role> convertToEntityAttribute(String rolesText) {
        if (StringUtils.isEmpty(rolesText)) {
            return Set.of();
        }

        return Arrays
                .stream(rolesText.split(","))
                .map(Role::valueOf)
                .collect(Collectors.toSet());
    }
}
