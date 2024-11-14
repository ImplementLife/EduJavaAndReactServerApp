package il.test.TestWithReact.data.convert;

import il.test.TestWithReact.data.security.Role;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.HashSet;
import java.util.Set;

@Component
@Converter(autoApply = true)
public class RoleStringConverter implements AttributeConverter<Set<Role>, String> {
    @Override
    public String convertToDatabaseColumn(Set<Role> rolesAsSet) {
        if (rolesAsSet == null) return "";
        StringBuilder builder = new StringBuilder();
        for (Role role : rolesAsSet) {
            builder.append(role.getId());
        }
        return builder.toString();
    }

    @Override
    public Set<Role> convertToEntityAttribute(String rolesAsStr) {
        if (Strings.isBlank(rolesAsStr)) return new HashSet<>();
        Set<Role> rolesAsSet = new HashSet<>();
        for (char id : rolesAsStr.toCharArray()) {
            rolesAsSet.add(Role.getById(id));
        }
        return rolesAsSet;
    }
}
