package il.test.TestWithReact.service;

import java.io.InputStream;
import java.io.OutputStream;

public interface FileStorageService {
    void putFile(String name, InputStream inputStream, long contentLength);
    void getFile(String name, OutputStream outputStream);
}
