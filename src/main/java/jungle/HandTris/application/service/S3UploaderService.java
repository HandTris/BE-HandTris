package jungle.HandTris.application.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface S3UploaderService {
    String upload(MultipartFile multipartFile, String dirName) throws IOException;
}
