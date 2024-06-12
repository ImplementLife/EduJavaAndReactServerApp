package il.test.TestWithReact.data.entity.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;

public interface UserValidate {
    @NotBlank
    @Email
    String getEmail();
    @NotBlank
    String getFirstName();
    @NotBlank
    String getLastName();
    @NotNull
    Date getBirthDate();
    @Pattern(regexp="(^$|[0-9]{10})", message = "Phone number must be 10 digits")
    String getPhoneNumber();
}
