package com.ricardo.fileupload.controllers;

import com.ricardo.fileupload.data.DownloadData;
import com.ricardo.fileupload.dtos.FileUploadRequest;
import com.ricardo.fileupload.dtos.FileUploadResponse;
import com.ricardo.fileupload.services.FileService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpHeaders;


@RestController
@RequestMapping("/api/v1/files")

public class FileController {

    private final FileService fileService;

    public FileController(FileService fileUploadService) {
        this.fileService = fileUploadService;
    }

    @PostMapping("/upload")
    public ResponseEntity<FileUploadResponse> uploadFile(@ModelAttribute FileUploadRequest fileUploadRequest) {
        FileUploadResponse fileUploadResponse = fileService.uploadFile(fileUploadRequest);
        return ResponseEntity.ok(fileUploadResponse);
    }

    @GetMapping("/download")
    public ResponseEntity<byte[]> downloadFile(@RequestParam("id") Long id) {
        DownloadData downloadData = fileService.downloadFile(id);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Disposition", "attachment; filename=" + downloadData.getFileName());

        return new ResponseEntity<>(downloadData.getFileBytes(), headers, HttpStatus.OK);
    }

}
