package org.oladushek.module25.service;

import org.oladushek.module25.entity.FileEntity;
import org.oladushek.module25.exception.UploadFileException;
import org.oladushek.module25.mapper.FileMapper;
import org.oladushek.module25.rest.dto.FileResponseDto;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
public interface FileService extends GenericService<FileEntity, Long> {

    void uploadFileToS3(String bucketName, MultipartFile file) throws UploadFileException;

    Mono<List<FileEntity>> getAllByBucket(String bucket);

    Mono<List<FileEntity>> getAllByUserId(Long id);

    InputStreamResource downloadFile(String bucket, String filename);
}
