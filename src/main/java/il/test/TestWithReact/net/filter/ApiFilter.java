package il.test.TestWithReact.net.filter;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
@Order(Ordered.LOWEST_PRECEDENCE)
public class ApiFilter implements Filter {

    @Override
    public void doFilter(
        ServletRequest request,
        ServletResponse response,
        FilterChain chain
    ) throws IOException, ServletException {
//        HttpServletRequest req = (HttpServletRequest) request;
//        String requestURI = req.getRequestURI();
//
//        if (
//            requestURI.contains(".")
//            || requestURI.startsWith("/api")
//            || requestURI.startsWith("/res")
//            || requestURI.startsWith("/open-api")
//            || requestURI.startsWith("/h2-console")
//        ) {
            chain.doFilter(request, response);
//        } else {
//            // all requests not api or static will be forwarded to index page.
//            request.getRequestDispatcher("/index.html").forward(request, response);
//        }
    }
}
