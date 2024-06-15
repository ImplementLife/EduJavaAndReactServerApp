package il.test.TestWithReact.net.except;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthEntryPoint {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public void commence(
        HttpServletRequest request,
        HttpServletResponse response,
        JWTVerificationException authException
    ) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        ExceptDetails details = new ExceptDetails(request);
        details.setMessage(authException.getMessage());
        OBJECT_MAPPER.writeValue(response.getOutputStream(), details);
    }
}
