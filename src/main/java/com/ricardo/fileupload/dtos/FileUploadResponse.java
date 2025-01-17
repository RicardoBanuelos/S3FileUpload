package com.ricardo.fileupload.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FileUploadResponse {
    Long id;
    @JsonProperty("user_id")
    Long userId;
    @JsonProperty("s3_file_key")
    String s3FileKey;

    public FileUploadResponse() {
    }

    public FileUploadResponse(Long id, Long userId, String fileUrl) {
        this.id = id;
        this.userId = userId;
        this.s3FileKey = fileUrl;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getS3FileKey() {
        return s3FileKey;
    }

    public void setS3FileKey(String s3FileKey) {
        this.s3FileKey = s3FileKey;
    }
}
