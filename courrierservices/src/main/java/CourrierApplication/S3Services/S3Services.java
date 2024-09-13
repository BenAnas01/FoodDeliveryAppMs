package CourrierApplication.S3Services;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class S3Services {


    private final AmazonS3 s3Client;


    public S3Services(@Value("${aws.access-key}") String accessKey ,

                      @Value("${aws.secret-key}")String secretKey,

                      @Value("${aws.region}") String region) {
        BasicAWSCredentials awsCredentials = new BasicAWSCredentials(accessKey,secretKey);

        this.s3Client = AmazonS3ClientBuilder.standard()
                .withRegion(region)
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .build();
    }

    public String uploadFile(String bucketName, String key, File file) {


        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, file);



        s3Client.putObject(putObjectRequest);

        return s3Client.getUrl(bucketName, key).toString();
    }





}
