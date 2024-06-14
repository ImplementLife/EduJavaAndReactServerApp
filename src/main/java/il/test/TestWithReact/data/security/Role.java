package il.test.TestWithReact.data.security;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    NONE('N', "ROLE_NONE"),
    USER('U', "ROLE_USER"),
    BUSINESS_USER('B', "ROLE_BUSINESS_USER"),
    MANAGER('M', "ROLE_MANAGER"),
    ADMIN('A', "ROLE_ADMIN");

    private final char id;
    private final String name;

    Role(char id, String name) {
        this.id = id;
        this.name = name;
    }

    public char getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String getAuthority() {
        return name;
    }

    public static Role getById(char id) {
        for (Role value : values()) {
            if (value.id == id) return value;
        }
        throw new IllegalArgumentException("Not exist role with id=" + id);
    }

}
