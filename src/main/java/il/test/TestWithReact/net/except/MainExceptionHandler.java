package il.test.TestWithReact.net.except;

import il.test.TestWithReact.util.StackTraceFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class MainExceptionHandler {
    @Autowired
    private StackTraceFilter stackTraceFilter;

    @ExceptionHandler(UnsupportedOperationException.class)
    public ResponseEntity<ExceptDetails> handleUnsupportedOperationException(UnsupportedOperationException ex, HttpServletRequest request) {
        ExceptDetails details = new ExceptDetails(request);
        log.error("Handled exception by url: " + details.getPath(), stackTraceFilter.filter(ex));
        details.setMessage(ex.getClass());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(details);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptDetails> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, HttpServletRequest request) {
        ExceptDetails details = new ExceptDetails(request);
        log.error("Handled exception by url: " + details.getPath(), stackTraceFilter.filter(ex));

        String simpleName = ex.getTarget().getClass().getSimpleName();
        Map<String, Object> msg = new HashMap<>();
        for (ObjectError error : ex.getBindingResult().getAllErrors()) {
            if (error.getArguments()[0] instanceof DefaultMessageSourceResolvable args) {
                String code = args.getCode();
                msg.put(simpleName + "." + code, error.getDefaultMessage());
            }
        }

        details.setMessage(msg);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(details);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ExceptDetails> handleIllegalArgumentException(IllegalArgumentException ex, HttpServletRequest request) {
        ExceptDetails details = new ExceptDetails(request);
        log.error("Handled exception by url: " + details.getPath(), stackTraceFilter.filter(ex));

        details.setMessage(ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(details);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptDetails> handleException(Exception ex, HttpServletRequest request) {
        ExceptDetails details = new ExceptDetails(request);
        log.error("Handled exception by url: " + details.getPath(), stackTraceFilter.filter(ex));

        details.setMessage(ex.getMessage());
        return ResponseEntity.internalServerError().body(details);
    }
}
