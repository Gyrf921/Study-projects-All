package org.oladushek.module25.rest;

import lombok.RequiredArgsConstructor;
import org.oladushek.module25.exception.DownloadFileException;
import org.oladushek.module25.exception.UploadFileException;
import org.oladushek.module25.mapper.FileMapper;
import org.oladushek.module25.rest.dto.FileRequestDto;
import org.oladushek.module25.rest.dto.FileResponseDto;
import org.oladushek.module25.service.EventService;
import org.oladushek.module25.service.FileService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/files")
@RequiredArgsConstructor
public class FileControllerV1 {

    private final FileService fileService;
    private final FileMapper fileMapper;


    @GetMapping("/my")
    public Mono<List<FileResponseDto>> getMyFiles(Principal principal) {
        return fileService.getAllByBucket(principal.getName()).map(
                list -> list.stream()
                        .map(fileMapper::map)
                        .toList()
        );
    }

    @GetMapping("/download/{filename}")
    public ResponseEntity<Resource> downloadFile(Principal principal, @PathVariable String filename) {
        try {
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(fileService.downloadFile(principal.getName(), filename));
        } catch (DownloadFileException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping
    public ResponseEntity<String> upload(Principal principal, @RequestParam("file") MultipartFile file) {
        try {
            fileService.uploadFileToS3(principal.getName(), file);
            return ResponseEntity.ok("File uploaded successfully");
        } catch (UploadFileException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }


    @GetMapping
    @PreAuthorize("hasAnyAuthority('MODERATOR', 'ADMIN')")
    public Mono<List<FileResponseDto>> getAllFiles() {
        return fileService.getAll().map(
                list -> list.stream()
                        .map(fileMapper::map)
                        .toList()
        );
    }

    @GetMapping("/user/{id}")
    @PreAuthorize("hasAnyAuthority('MODERATOR', 'ADMIN')")
    public Mono<List<FileResponseDto>> getAllFilesByUserId(@PathVariable Long id) {
        return fileService.getAllByUserId(id).map(
                list -> list.stream()
                        .map(fileMapper::map)
                        .toList()
        );
    }

    @GetMapping("/{fileId}")
    @PreAuthorize("hasAnyAuthority('MODERATOR', 'ADMIN')")
    public Mono<FileResponseDto> getFileById(@PathVariable Long fileId) {
        return fileService.getById(fileId)
                .map(fileMapper::map);
    }


    @PutMapping("/{fileId}")
    @PreAuthorize("hasAnyAuthority('MODERATOR', 'ADMIN')")
    public Mono<FileResponseDto> updateFile(@PathVariable Long fileId, @RequestBody FileRequestDto fileInfo) {
        // Обновление любого файла
        return Mono.empty();
    }


    @DeleteMapping("/{fileId}")
    @PreAuthorize("hasAnyAuthority('MODERATOR', 'ADMIN')")
    public Mono<Void> deleteFile(@PathVariable Long fileId) {
        fileService.delete(fileId);
        return Mono.empty();
    }






}
