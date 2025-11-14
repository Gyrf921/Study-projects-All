package org.oladushek.module25.service.impl;

import io.minio.*;
import io.minio.messages.Item;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.oladushek.module25.entity.FileEntity;
import org.oladushek.module25.exception.DownloadFileException;
import org.oladushek.module25.exception.UploadFileException;
import org.oladushek.module25.service.EventService;
import org.oladushek.module25.service.FileService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final EventService eventService;
    private final MinioClient minioClient;

    @Override
    public void uploadFileToS3(String bucketName, MultipartFile file) {
        try {
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(file.getName())
                            .stream(file.getInputStream(), file.getSize(), -1)
                            .contentType(file.getContentType())
                            .build()
            );
        } catch (Exception e) {
            throw new UploadFileException(e.getMessage());
        }

    }

    @Override
    public Mono<List<FileEntity>> getAllByBucket(String bucket) {
        return null;
    }

    @Override
    public Mono<List<FileEntity>> getAllByUserId(Long id) {
        return null;
    }

    @Override
    public InputStreamResource downloadFile(String bucket, String filename) {
        try {
            InputStream stream = minioClient.getObject(
                    GetObjectArgs.builder().bucket(bucket).object(filename).build()
            );
            return new InputStreamResource(stream);
        } catch (Exception e) {
            throw new DownloadFileException(e.getMessage());
        }
    }

    @Override
    public Mono<FileEntity> getById(Long aLong) {
        return null;
    }

    @Override
    public Mono<List<FileEntity>> getAll() {
        List<String> fileNames = new ArrayList<>();
        Iterable<Result<Item>> items = minioClient.listObjects(
                ListObjectsArgs.builder().bucket(bucket).build()
        );
        for (Result<Item> item : items) {
            fileNames.add(item.get().objectName());
        }
        return ResponseEntity.ok(fileNames);

        return null;
    }

    @Override
    public Mono<FileEntity> create(FileEntity fileEntity) {
        return null;
    }

    @Override
    public Mono<FileEntity> update(Long aLong, FileEntity fileEntity) {
        return null;
    }

    @Override
    public void delete(Long aLong) {

    }
}
