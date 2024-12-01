package il.test.TestWithReact.net.controller;

import il.test.TestWithReact.data.entity.db.User;
import il.test.TestWithReact.data.entity.dto.LoginRequest;
import il.test.TestWithReact.data.entity.dto.SecDto;
import il.test.TestWithReact.service.JwtAuthService;
import il.test.TestWithReact.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private JwtAuthService jwtAuthService;

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<SecDto> login(@RequestBody LoginRequest loginRequest) {
        User user = userService.loginUser(loginRequest);
        SecDto secDto = jwtAuthService.createToken(user);
        return ResponseEntity.ok(secDto);
    }

    @PostMapping("/refresh")
    public ResponseEntity<SecDto> refresh(@RequestBody SecDto secDto) {
        SecDto refreshedSecDto = jwtAuthService.refresh(secDto);
        return ResponseEntity.ok(refreshedSecDto);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestBody SecDto secDto) {
        jwtAuthService.logout(secDto);
        return ResponseEntity.ok().build();
    }
}

