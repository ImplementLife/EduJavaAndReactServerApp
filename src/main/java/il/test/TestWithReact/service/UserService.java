package il.test.TestWithReact.service;

import il.test.TestWithReact.data.entity.db.User;
import il.test.TestWithReact.data.entity.dto.LoginRequest;
import il.test.TestWithReact.data.repo.UserRepo;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public User loginUser(LoginRequest loginRequest) {
        User user = userRepo.findByEmail(loginRequest.getId())
            .orElseThrow(() -> new IllegalArgumentException("Unknown user"));

        if (passwordEncoder.matches(loginRequest.getPass(), user.getPass())) {
            return user;
        }
        throw new IllegalArgumentException("Invalid password");
    }

    @Data
    public static class UpdateUserRequest {
        
    }

    public void updateUser(UpdateUserRequest updateUserRequest) {

    }

}
