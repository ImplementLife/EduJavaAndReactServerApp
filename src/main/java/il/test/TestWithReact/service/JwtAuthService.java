package il.test.TestWithReact.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import il.test.TestWithReact.data.convert.RoleStringConverter;
import il.test.TestWithReact.data.entity.db.User;
import il.test.TestWithReact.data.entity.dto.SecDto;
import il.test.TestWithReact.data.security.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;

import static java.lang.Long.valueOf;

@Service
public class JwtAuthService {
    @Autowired
    private RoleStringConverter roleStringConverter;

    private String accessSecretKey;
    private String refreshSecretKey;

    private Algorithm accessAlgorithm;
    private JWTVerifier accessVerifier;

    private Algorithm refreshAlgorithm;
    private JWTVerifier refreshVerifier;

    // K: (user id), V: (list with not expired tokens)
    private final Map<Long, List<SecDto>> tokensBlacklist = new HashMap<>();

    @PostConstruct
    protected void init() {
        accessSecretKey = UUID.randomUUID().toString();
        refreshSecretKey = UUID.randomUUID().toString();


        accessSecretKey = Base64.getEncoder().encodeToString(accessSecretKey.getBytes());
        refreshSecretKey = Base64.getEncoder().encodeToString(refreshSecretKey.getBytes());

        accessAlgorithm = Algorithm.HMAC256(accessSecretKey);
        accessVerifier = JWT.require(accessAlgorithm).build();

        refreshAlgorithm = Algorithm.HMAC256(refreshSecretKey);
        refreshVerifier = JWT.require(refreshAlgorithm).build();
    }

    public SecDto createToken(User user) {
        SecDto secDto = new SecDto();
        secDto.setAccessToken(createAccessToken(user));
        secDto.setRefreshToken(createRefreshToken(user));
        return secDto;
    }

    public SecDto refresh(SecDto secDto) {

        throw new UnsupportedOperationException();
    }

    public void logout(SecDto secDto) {

        throw new UnsupportedOperationException();
    }

    private String createAccessToken(User user) {
        JWTCreator.Builder builder = createToken(user, 3_600_000L);// 1 hour
        String token = builder
            .withClaim("roles", roleStringConverter.convertToDatabaseColumn(user.getRoles()))
            .sign(accessAlgorithm);
        return token;
    }

    private String createRefreshToken(User user) {
        JWTCreator.Builder builder = createToken(user, 2_592_000_000L);// 30 days
        return builder.sign(refreshAlgorithm);
    }

    private JWTCreator.Builder createToken(User user, long lifetime) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + lifetime);

        JWTCreator.Builder builder = JWT.create()
            .withIssuedAt(now)
            .withExpiresAt(validity)
            .withSubject(user.getId().toString());
        return builder;
    }

    public Authentication validateAccessToken(String token) {
        DecodedJWT decoded = accessVerifier.verify(token);
        Long userId = valueOf(decoded.getSubject());
        Set<Role> roles = roleStringConverter.convertToEntityAttribute(decoded.getClaim("roles").asString());

        return new UsernamePasswordAuthenticationToken(userId, null, roles);
    }
}
