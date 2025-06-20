package com.liuao.game_card_sell.controller;

import com.liuao.game_card_sell.dto.ApiResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/upload")
public class FileUploadController {

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Value("${file.access-path}")
    private String accessPath;

    @PostMapping
    public ResponseEntity<ApiResponse<?>> uploadFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "oldFileUrl", required = false) String oldFileUrl) {
        try {
            // 确保上传目录存在
            Path uploadPath = Paths.get(System.getProperty("user.dir"), uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // 生成唯一文件名
            String originalFilename = file.getOriginalFilename();
            String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
            String newFilename = UUID.randomUUID().toString() + fileExtension;

            // 保存文件
            Path filePath = uploadPath.resolve(newFilename);
            Files.copy(file.getInputStream(), filePath);

            // 返回访问URL
            String fileUrl = "/images/" + newFilename;

            // 删除旧图片, url /images/ 对应 路径/xxx/xxxx/...../uploadPath/
            if (oldFileUrl != null &&!oldFileUrl.isEmpty()) {
                Path oldPath = uploadPath.resolve(Paths.get(oldFileUrl).getFileName());
                if (Files.exists(oldPath)) {
                    Files.delete(oldPath);
                }
            }

            return ResponseEntity.ok(ApiResponse.success(Map.of(
                    "url", fileUrl,
                    "path", filePath.toString()
            )));
        } catch (IOException e) {
            return ResponseEntity.status(500).body(ApiResponse.error(500, "文件上传失败"));
        }
    }
}