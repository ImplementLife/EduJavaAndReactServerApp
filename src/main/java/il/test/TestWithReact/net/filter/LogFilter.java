package il.test.TestWithReact.net.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Slf4j
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class LogFilter implements Filter {
    @Override
    public void doFilter(
        ServletRequest request,
        ServletResponse response,
        FilterChain chain
    ) throws IOException, ServletException {
        chain.doFilter(request, response);

        HttpServletResponse res = (HttpServletResponse) response;
        HttpServletRequest req = (HttpServletRequest) request;
        StringBuffer requestURL = req.getRequestURL();
        String remoteAddr = req.getRemoteAddr();
        Map<String, String[]> parameterMap = req.getParameterMap();
        Map<String, String[]> headersMap = getHeadersMap(req);
        String remoteUser = req.getRemoteUser();
        if (res.getStatus() == HttpServletResponse.SC_NOT_FOUND) {
            log.info("unknown path: {} : {} : {}", requestURL, remoteAddr, remoteUser);
        }
    }

    private Map<String, String[]> getHeadersMap(HttpServletRequest req) {
        Map<String, String[]> result = new HashMap<>();

        Enumeration<String> headerNames = req.getHeaderNames();
        for (Iterator<String> itNames = headerNames.asIterator(); itNames.hasNext(); ) {
            String headerName = itNames.next();
            List<String> values = new ArrayList<>();
            Enumeration<String> headers = req.getHeaders(headerName);
            for (Iterator<String> itValues = headers.asIterator(); itValues.hasNext(); ) {
                String headerValue = itValues.next();
                values.add(headerValue);
            }
            String[] strings = values.toArray(values.toArray(new String[0]));
            result.put(headerName, strings);
        }
        return result;
    }
}
