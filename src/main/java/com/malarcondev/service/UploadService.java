package com.malarcondev.service;

import com.malarcondev.mapper.CustomerMapper;
import com.malarcondev.repository.CustomerRepository;
import com.malarcondev.s3.S3Bucket;
import com.malarcondev.s3.S3Service;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UploadService {
    private final S3Service s3Service;
    private final S3Bucket s3Bucket;
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public void uploadCustomerProfileImage(Integer customerId,
                                           MultipartFile file) throws Exception {
        this.checkIfCustomerExistsOrThrow(customerId);
        String profileImageId = UUID.randomUUID().toString();
        try {
            s3Service.putObject(
                    s3Bucket.getName(),
                    "profile-images/%s/%s".formatted(customerId, profileImageId),
                    file.getBytes()
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        customerRepository.updateCustomerProfileImageId(profileImageId, customerId);
    }

    public byte[] getCustomerProfileImage(Integer customerId) throws Exception {
        var customer = customerRepository.findById(customerId)
                .map(customerMapper::toDto)
                .orElseThrow(() -> new Exception(
                        "customer with id [%s] not found".formatted(customerId)
                ));

        if (StringUtils.isBlank(customer.getProfileImageId())) {
            throw new Exception(
                    "customer with id [%s] profile image not found".formatted(customerId));
        }

        byte[] profileImage = s3Service.getObject(
                s3Bucket.getName(),
                "profile-images/%s/%s".formatted(customerId, customer.getProfileImageId())
        );
        return profileImage;
    }

    private void checkIfCustomerExistsOrThrow(Integer customerId) throws Exception {
        if (!customerRepository.findCustomerById(customerId)) {
            throw new Exception(
                    "customer with id [%s] not found".formatted(customerId)
            );
        }
    }
}
