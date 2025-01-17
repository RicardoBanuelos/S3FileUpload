package com.ricardo.fileupload.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "file_metadata")
public class FileMetadata {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    @JsonProperty("user_id")
    private Long userId;

    @Column(name = "file_name")
    @JsonProperty("file_name")
    private String fileName;

    @Column(name = "content_type")
    @JsonProperty("content_type")
    private String contentType;

    @Column(name = "size")
    private Long size;

    @Column(name = "uploaded_at")
    @JsonProperty("content_type")
    private LocalDateTime uploadedAt;

    @Column(name = "s3_file_key")
    @JsonProperty("s3_file_key")
    private String s3FileKey;

    public FileMetadata() {
    }

    public FileMetadata(Long id, Long userId, String fileName, String contentType, Long size, LocalDateTime uploaded_at, String s3FileKey) {
        this.id = id;
        this.userId = userId;
        this.fileName = fileName;
        this.contentType = contentType;
        this.size = size;
        this.uploadedAt = uploaded_at;
        this.s3FileKey = s3FileKey;
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

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public LocalDateTime getUploadedAt() {
        return uploadedAt;
    }

    public void setUploadedAt(LocalDateTime uploadedAt) {
        this.uploadedAt = uploadedAt;
    }

    public String getS3FileKey() {
        return s3FileKey;
    }

    public void setS3FileKey(String s3FileKey) {
        this.s3FileKey = s3FileKey;
    }
}
