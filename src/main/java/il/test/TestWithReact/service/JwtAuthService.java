package il.test.TestWithReact.service;

import com.auth0.jwt.JWT;
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
import java.util.Base64;
import java.util.Date;
import java.util.Set;

import static java.lang.Long.valueOf;

@Service
public class JwtAuthService {
    @Value("${security.jwt.token.secret-key:secret-key}")
    private String secretKey;
    @Autowired
    private RoleStringConverter roleStringConverter;

    private Algorithm algorithm;
    private JWTVerifier verifier;

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
        algorithm = Algorithm.HMAC256(secretKey);
        verifier = JWT.require(algorithm).build();
    }

    public SecDto createToken(User user) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + 3600000); // 1 hour

        String token = JWT.create()
            .withSubject(user.getId().toString())
            .withIssuedAt(now)
            .withExpiresAt(validity)
            .withClaim("roles", roleStringConverter.convertToDatabaseColumn(user.getRoles()))
            .sign(algorithm);

        SecDto secDto = new SecDto();
        secDto.setAccessToken(token);
        return secDto;
    }

    public Authentication validateToken(String token) {
        DecodedJWT decoded = verifier.verify(token);
        User user = new User();
        user.setId(valueOf(decoded.getSubject()));
        Set<Role> roles = roleStringConverter.convertToEntityAttribute(decoded.getClaim("roles").asString());
        user.setRoles(roles);

        return new UsernamePasswordAuthenticationToken(user, null, roles);
    }
}
