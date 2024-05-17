package com.ssg.starroadadmin.global.component;

import com.amazonaws.SdkClientException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.File;

@Disabled
@DisplayName("NCP Object Storage Amazon S3 API 활용 예제")
class NCPObjectStorageTest {

    private String accessKey;// = {{"cloud.aws.credentials.access-key"}};
    private String secretKey;// = {{"cloud.aws.credentials.secret-key"}};
    private String regionName = "kr-standard";
    private String endPoint = "https://kr.object.ncloudstorage.com";
    private String bucketName = "ssg-starroad";

    @Test
    @DisplayName("Java용 AWS SDK")
    public void NCP_DOCUMENT_EXAMPLE() {

        // S3 client
        final AmazonS3 amazonS3 = AmazonS3ClientBuilder.standard()
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(endPoint, regionName))
                .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKey, secretKey)))
                .build();

        // create folder
        String folderName = "ssg/";

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(0L);
        objectMetadata.setContentType("application/x-directory");
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, folderName, new ByteArrayInputStream(new byte[0]), objectMetadata);

        try {
            amazonS3.putObject(putObjectRequest);
            System.out.format("Folder %s has been created.\n", folderName);
        } catch (AmazonS3Exception e) {
            e.printStackTrace();
        } catch (SdkClientException e) {
            e.printStackTrace();
        }

        // upload local file
        String objectName = "ssg/user/profile/양성준 캐릭터12.png";
        // 양성준 캐릭터.png 파일을 업로드
        String filePath = "/Users/jjuuuunn/Desktop/양성준 캐릭터.png";

        try {
            amazonS3.putObject(bucketName, objectName, new File(filePath));
            System.out.format("Object %s has been created.\n", objectName);
        } catch (AmazonS3Exception e) {
            e.printStackTrace();
        } catch (SdkClientException e) {
            e.printStackTrace();
        }
    }
}
