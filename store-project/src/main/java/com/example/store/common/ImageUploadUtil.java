package com.example.store.common;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Component
public class ImageUploadUtil {
    private final String uploadDir = System.getProperty("user.dir") + "/uploads";

    public String saveImage(MultipartFile file) {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("파일이 비어있습니다.");
        }
        try {
            File dir = new File(uploadDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            // 파일명 UUID 사용
            String originalFilename = file.getOriginalFilename();
            String extension = getFileExtension(originalFilename);
            String savedFileName = UUID.randomUUID() + "." + extension;

            // 저장
            File savedFile = new File(uploadDir + "/" + savedFileName);
            file.transferTo(savedFile);

            // 저장된 파일 경로를 반환
            return savedFileName;
        } catch (IOException e) {
            throw new RuntimeException("이미지 저장 실패", e);
        }
    }

    private String getFileExtension(String fileName) {
        if (fileName == null) {
            throw new RuntimeException("파일명 오류");
        }
        int dotIndex = fileName.lastIndexOf(".");
        if (dotIndex == -1) {
            throw new RuntimeException("파일 확장자 오류");
        }
        return fileName.substring(dotIndex + 1);
    }
}
