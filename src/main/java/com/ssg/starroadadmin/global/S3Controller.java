package com.ssg.starroadadmin.global;

import com.ssg.starroadadmin.global.util.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class S3Controller {

    private final S3Uploader s3Uploader;

    @PostMapping("/upload")
    public ResponseEntity<List<String>> uploadFile(@RequestParam("file") MultipartFile[] file,
                                                   @RequestParam("dirName") String dirName) {
        return ResponseEntity.ok(s3Uploader.upload(file, dirName));
    }

    @PostMapping("/delete")
    public ResponseEntity<String> deleteFile(@RequestParam("fileName") String fileName) {
        s3Uploader.delete(fileName);
        return ResponseEntity.ok("File deleted successfully!");
    }
}
