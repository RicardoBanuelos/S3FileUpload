package com.ricardo.fileupload.repositories;

import com.ricardo.fileupload.entities.FileMetadata;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileMetadataRepository extends JpaRepository<FileMetadata, Long> {
}
