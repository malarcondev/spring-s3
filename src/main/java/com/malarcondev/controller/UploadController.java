package com.malarcondev.controller;

import com.malarcondev.service.UploadService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/v1/upload")
@AllArgsConstructor
public class UploadController {

    private final UploadService uploadService;

    @PostMapping(
            value = "{customerId}/profile-image",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public void uploadCustomerProfileImage(
            @PathVariable("customerId") Integer customerId,
            @RequestParam("file")MultipartFile file) throws Exception {

        uploadService.uploadCustomerProfileImage(customerId, file);
    }

    @GetMapping("{customerId}/profile-image")
    public byte[] getCustomerProfileImage(
            @PathVariable("customerId") Integer customerId) throws Exception {
        return uploadService.getCustomerProfileImage(customerId);
    }
}
