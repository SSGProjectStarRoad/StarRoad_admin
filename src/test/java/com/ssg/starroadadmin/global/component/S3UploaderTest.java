package com.ssg.starroadadmin.global.component;

import com.amazonaws.services.s3.AmazonS3;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class S3UploaderTest {

    @Mock
    private AmazonS3 amazonS3;

    private S3Uploader s3Uploader;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        s3Uploader = new S3Uploader(amazonS3);
    }

    @Test
    @Disabled
    void givenMultipartFile_whenUpload_thenAmazonS3UploadCalledWithCorrectArguments() {
        // Given
        String dirName = "testDir";
        String fileName = "testFile.txt";
        MockMultipartFile[] multipartFile = new MockMultipartFile[]{
                new MockMultipartFile("file", fileName, "text/plain", "Hello, World!".getBytes())
        };

        // When
        s3Uploader.upload(multipartFile, dirName);

        // Then
        verify(amazonS3, times(1)).putObject(any(), any(), any(), any());
    }
}
