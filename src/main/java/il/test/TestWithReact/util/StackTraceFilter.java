package il.test.TestWithReact.util;

import il.test.TestWithReact.Boot;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;

@Component
public class StackTraceFilter {
    private String applicationPackage;

    @PostConstruct
    private void postConstruct() {
        applicationPackage = Boot.class.getPackageName();
    }

    public <T extends Exception> T filter(T e) {
        try {
            @SuppressWarnings("unchecked")
            T filteredException = (T) e.getClass()
                .getConstructor(String.class, Throwable.class)
                .newInstance(e.getMessage(), e.getCause());

            StackTraceElement[] filteredStackTrace = Arrays.stream(e.getStackTrace())
                .filter(element -> element.getClassName().startsWith(applicationPackage))
                .toArray(StackTraceElement[]::new);

            filteredException.setStackTrace(filteredStackTrace);
            return filteredException;
        } catch (Exception ex) {
            return e;
        }
    }

}
