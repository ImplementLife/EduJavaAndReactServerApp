package il.test.TestWithReact.service.sequrity;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import il.test.TestWithReact.data.entity.db.User;

import java.util.Base64;
import java.util.Date;

public class JWTProcessor {
    private final long lifetime;
    private final Algorithm algorithm;
    private final JWTVerifier verifier;

    public JWTProcessor(String secretKey, long lifetime) {
        this.lifetime = lifetime;
        String encodedSecretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
        this.algorithm = Algorithm.HMAC256(encodedSecretKey);
        this.verifier = JWT.require(algorithm).build();
    }

    public DecodedJWT isValid(String token) {
        return verifier.verify(token);
    }

    public JWTCreator.Builder createToken(User user) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + lifetime);

        JWTCreator.Builder builder = JWT.create()
            .withIssuedAt(now)
            .withExpiresAt(validity)
            .withSubject(user.getId().toString());
        return builder;
    }

    public String sign(JWTCreator.Builder builder) {
        return builder.sign(algorithm);
    }
}
