package il.test.TestWithReact.config;

import il.test.TestWithReact.service.FileStorageService;
import il.test.TestWithReact.service.impl.aws.S3FSS;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class BeanConfig {
    @Bean
    public FileStorageService fileStorageService(
        @Value("${aws.s3.bucket-name}") String bucketName,
        @Value("${aws.s3.region}") String region,
        @Value("${aws.access-key-id}") String accessKeyId,
        @Value("${aws.secret-access-key}") String secretAccessKey
    ) {
        log.info("aws.s3.bucket-name: " + bucketName);
        return new S3FSS(bucketName, region, accessKeyId, secretAccessKey);
    }
}
