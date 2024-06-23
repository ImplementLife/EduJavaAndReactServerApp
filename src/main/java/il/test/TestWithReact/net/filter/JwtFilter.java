package il.test.TestWithReact.net.filter;

import com.auth0.jwt.exceptions.JWTVerificationException;
import il.test.TestWithReact.net.except.AuthEntryPoint;
import il.test.TestWithReact.service.JwtAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {
    @Autowired
    private JwtAuthService jwtAuthService;
    @Autowired
    private AuthEntryPoint entryPoint;

    @Override
    protected void doFilterInternal(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain chain
    ) throws ServletException, IOException {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (header != null) {
            try {
                SecurityContextHolder.getContext().setAuthentication(jwtAuthService.validateAccessToken(header));
            } catch (JWTVerificationException e) {
                SecurityContextHolder.clearContext();
                entryPoint.commence(request, response, e);
                return;
            } catch (Exception e) {
                SecurityContextHolder.clearContext();
                throw e;
            }
        }

        chain.doFilter(request, response);
    }
}
