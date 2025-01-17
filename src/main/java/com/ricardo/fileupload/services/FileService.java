package com.ricardo.fileupload.services;

import com.ricardo.fileupload.data.DownloadData;
import com.ricardo.fileupload.dtos.FileUploadRequest;
import com.ricardo.fileupload.dtos.FileUploadResponse;
import com.ricardo.fileupload.entities.FileMetadata;
import com.ricardo.fileupload.exceptions.AppException;
import com.ricardo.fileupload.repositories.FileMetadataRepository;
import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Service
public class FileService {
    private final S3Service s3Service;
    private final FileMetadataRepository fileMetadataRepository;

    public FileService(S3Service s3Service, FileMetadataRepository fileMetadataRepository) {
        this.s3Service = s3Service;
        this.fileMetadataRepository = fileMetadataRepository;
    }

    public FileUploadResponse uploadFile(FileUploadRequest fileUploadRequest) {
        String s3FileKey = s3Service.uploadFile(fileUploadRequest.getFile());

        MultipartFile file = fileUploadRequest.getFile();

        FileMetadata fileMetadata = new FileMetadata();
        fileMetadata.setUserId(fileUploadRequest.getUserId());
        fileMetadata.setFileName(file.getOriginalFilename());
        fileMetadata.setContentType(file.getContentType());
        fileMetadata.setUploadedAt(LocalDateTime.now());
        fileMetadata.setSize(file.getSize());
        fileMetadata.setS3FileKey(s3FileKey);

        FileMetadata savedFileMetadata = fileMetadataRepository.save(fileMetadata);

        FileUploadResponse fileUploadResponse = new FileUploadResponse();
        fileUploadResponse.setId(savedFileMetadata.getId());
        fileUploadResponse.setUserId(savedFileMetadata.getUserId());
        fileUploadResponse.setS3FileKey(savedFileMetadata.getS3FileKey());

        return fileUploadResponse;
    }

    public DownloadData downloadFile(Long id) {
        Optional<FileMetadata> fileMetadata = fileMetadataRepository.findById(id);

        if(fileMetadata.isEmpty()) {
            throw new AppException("Cant find file", HttpStatus.NOT_FOUND);
        }

        DownloadData downloadData = new DownloadData();
        downloadData.setFileName(fileMetadata.get().getS3FileKey());
        downloadData.setFileBytes(s3Service.downloadFile(fileMetadata.get().getS3FileKey()));

        return downloadData;
    }
}
