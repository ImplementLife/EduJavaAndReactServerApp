package il.test.TestWithReact.data.entity;

import il.test.TestWithReact.data.entity.doc.UserSchema;
import il.test.TestWithReact.data.entity.dto.UserDto;
import il.test.TestWithReact.data.entity.dto.UserValidate;

import java.util.Date;

public interface UserEntity extends UserDto, UserValidate, UserSchema {
    Long   getId();
    String getEmail();
    String getFirstName();
    String getLastName();
    Date   getBirthDate();
    String getAddress();
    String getPhoneNumber();

    void setId(Long id);
    void setEmail(String email);
    void setFirstName(String firstName);
    void setLastName(String lastName);
    void setBirthDate(Date birthDate);
    void setAddress(String address);
    void setPhoneNumber(String phoneNumber);
}
