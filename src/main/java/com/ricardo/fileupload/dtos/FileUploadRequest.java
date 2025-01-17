package com.ricardo.fileupload.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

public class FileUploadRequest {
    @NotNull(message = "User ID is required")
    Long userId;

    @NotNull(message = "File is empty")
    MultipartFile file;

    public FileUploadRequest() {
    }

    public FileUploadRequest(Long userId, MultipartFile file) {
        this.userId = userId;
        this.file = file;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}
