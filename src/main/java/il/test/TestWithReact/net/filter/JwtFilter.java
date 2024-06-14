package il.test.TestWithReact.net.filter;

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

    @Override
    protected void doFilterInternal(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain chain
    ) throws ServletException, IOException {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (header != null) {
            try {
                SecurityContextHolder.getContext().setAuthentication(jwtAuthService.validateToken(header));
            } catch (RuntimeException e) {
                SecurityContextHolder.clearContext();
                throw e;
            }
        }

        chain.doFilter(request, response);
    }

}
