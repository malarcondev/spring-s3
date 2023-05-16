package com.malarcondev.service;

import com.malarcondev.s3.S3Bucket;
import com.malarcondev.s3.S3Service;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UploadService {
    private final S3Service s3Service;
    private final S3Bucket s3Bucket;
    public void uploadCustomerProfileImage(Integer customerId,
                                           MultipartFile file) {
        try {
            String profileImageId = UUID.randomUUID().toString();
            s3Service.putObject(
                    s3Bucket.getName(),
                    "profile-images/%s/%s".formatted(customerId, profileImageId),
                    file.getBytes()
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // TODO: save image in db
    }

    public byte[] getCustomerProfileImage(Integer customerId) {
        String profileImageId = UUID.randomUUID().toString();
        byte[] profileImage = s3Service.getObject(
                s3Bucket.getName(),
                "profile-images/%s/%s".formatted(customerId, profileImageId)
        );
        return profileImage;
    }
}
