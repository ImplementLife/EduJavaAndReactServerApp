package il.test.TestWithReact.service;

import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.interfaces.DecodedJWT;
import il.test.TestWithReact.data.convert.RoleStringConverter;
import il.test.TestWithReact.data.entity.db.User;
import il.test.TestWithReact.data.entity.dto.SecDto;
import il.test.TestWithReact.data.security.Role;
import il.test.TestWithReact.service.sequrity.JWTProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Set;
import java.util.UUID;

import static java.lang.Long.valueOf;

@Slf4j
@Service
public class JwtAuthService {
    @Autowired
    private RoleStringConverter roleStringConverter;

    private JWTProcessor accessProcessor;
    private JWTProcessor refreshProcessor;

    @PostConstruct
    protected void init() {
        accessProcessor = new JWTProcessor(UUID.randomUUID().toString(), 3_600_000L); // 1 hour
        refreshProcessor = new JWTProcessor(UUID.randomUUID().toString(), 2_592_000_000L); // 30 days
    }

    public SecDto createToken(User user) {
        SecDto secDto = new SecDto();
        secDto.setAccessToken(createAccessToken(user));
        secDto.setRefreshToken(createRefreshToken(user));
        return secDto;
    }

    public SecDto refresh(SecDto secDto) {
        secDto.getRefreshToken();
        throw new UnsupportedOperationException();
    }

    public void logout(SecDto secDto) {
        throw new UnsupportedOperationException();
    }

    private String createAccessToken(User user) {
        JWTCreator.Builder builder = accessProcessor.createToken(user);
        builder.withClaim("roles", roleStringConverter.convertToDatabaseColumn(user.getRoles()));
        String token = accessProcessor.sign(builder);
        return token;
    }

    private String createRefreshToken(User user) {
        JWTCreator.Builder builder = refreshProcessor.createToken(user);
        return refreshProcessor.sign(builder);
    }


    public Authentication validateAccessToken(String token) {
        DecodedJWT decoded = accessProcessor.isValid(token);
        Long userId = valueOf(decoded.getSubject());
        Set<Role> roles = roleStringConverter.convertToEntityAttribute(decoded.getClaim("roles").asString());

        return new UsernamePasswordAuthenticationToken(userId, null, roles);
    }
    public Authentication getAuth(String tokenJWT) {
        return validateAccessToken(tokenJWT);
    }
}
