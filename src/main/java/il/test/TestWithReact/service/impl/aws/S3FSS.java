package il.test.TestWithReact.service.impl.aws;

import il.test.TestWithReact.service.FileStorageService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.core.sync.ResponseTransformer;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.InputStream;
import java.io.OutputStream;

public class S3FSS implements FileStorageService {

    private final S3Client s3Client;
    private final String bucketName;

    public S3FSS(
        String bucketName,
        String region,
        String accessKeyId,
        String secretAccessKey
    ) {
        this.bucketName = bucketName;
        AwsBasicCredentials awsCreds = AwsBasicCredentials.create(accessKeyId, secretAccessKey);
        this.s3Client = S3Client.builder()
            .region(Region.of(region))
            .credentialsProvider(StaticCredentialsProvider.create(awsCreds))
            .build();
    }

    public void putFile(String name, InputStream inputStream, long contentLength) {
        s3Client.putObject(
            PutObjectRequest.builder().bucket(bucketName).key(name).build(),
            RequestBody.fromInputStream(inputStream, contentLength)
        );
    }

    public void getFile(String name, OutputStream outputStream) {
        s3Client.getObject(
            GetObjectRequest.builder().bucket(bucketName).key(name).build(),
            ResponseTransformer.toOutputStream(outputStream)
        );
    }

    public Resource getFileAsResource(String name) {
        InputStream inputStream = s3Client.getObject(
            GetObjectRequest.builder().bucket(bucketName).key(name).build(),
            ResponseTransformer.toInputStream()
        );
        return new InputStreamResource(inputStream);
    }
}
