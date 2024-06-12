package il.test.TestWithReact.data.entity.dto;

import com.fasterxml.jackson.annotation.JsonView;

import java.util.Date;

public interface UserDto {
    @JsonView(ViewLevel.Public.class)
    Long getId();
    @JsonView(ViewLevel.Protected.class)
    String getEmail();
    @JsonView(ViewLevel.Public.class)
    String getFirstName();
    @JsonView(ViewLevel.Public.class)
    String getLastName();
    @JsonView(ViewLevel.Protected.class)
    Date getBirthDate();
    @JsonView(ViewLevel.Protected.class)
    String getAddress();
    @JsonView(ViewLevel.Protected.class)
    String getPhoneNumber();
}
