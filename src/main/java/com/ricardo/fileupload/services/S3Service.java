package com.ricardo.fileupload.services;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.ricardo.fileupload.exceptions.AppException;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class S3Service {
    @Value("${aws.s3.bucket-name}")
    private String bucketName;

    @Value("${aws.s3.access-key}")
    private String accessKey;

    @Value("${aws.s3.secret-key}")
    private String secretKey;

    private AmazonS3 s3Client;

    @PostConstruct
    private void initialize() {
        BasicAWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);
        s3Client = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .withRegion(Regions.US_WEST_2)
                .build();
    }

    public String uploadFile(MultipartFile file) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String todayDate = dateTimeFormatter.format(LocalDate.now());

        try {
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType(file.getContentType());
            objectMetadata.setContentLength(file.getSize());
            s3Client.putObject(bucketName, file.getOriginalFilename(), file.getInputStream(), objectMetadata);
        } catch (IOException e) {
            throw new AppException("Error uploading file to S3", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return file.getOriginalFilename();
    }

    public byte[] downloadFile(String s3FileKey) {
        S3Object s3Object = s3Client.getObject(bucketName, s3FileKey);
        try {
            InputStream inputStream = s3Object.getObjectContent();
            return inputStream.readAllBytes();

        } catch (IOException e) {
            throw new AppException("Error downloading from S3", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
