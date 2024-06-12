package il.test.TestWithReact.net.except;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Data
//@Schema(title = "ExceptDetails")
public class ExceptDetails {
    private Date timestamp;
    private String path;
    private Object message;

    public ExceptDetails(HttpServletRequest request) {
        timestamp = new Date();
        path = request.getRequestURL().toString();
    }
}
