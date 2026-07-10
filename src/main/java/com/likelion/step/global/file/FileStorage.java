package com.likelion.step.global.file;

import com.likelion.step.domain.verification.exception.VerificationErrorCode;
import com.likelion.step.global.error.exception.GeneralExeption;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Component
public class FileStorage {

  @Value("${file.upload-dir:./uploads}")
  private String uploadDir;

  private static final List<String> ALLOWED = List.of("image/jpeg", "image/png");

  public String store(MultipartFile file) {
    if (file == null || file.isEmpty()) {
      throw new GeneralExeption(VerificationErrorCode.EMPTY_FILE);
    }
    if (file.getContentType() == null || !ALLOWED.contains(file.getContentType())) {
      throw new GeneralExeption(VerificationErrorCode.INVALID_FILE_TYPE);
    }

    try {
      Path dir = Paths.get(uploadDir).toAbsolutePath().normalize();
      Files.createDirectories(dir);

      String ext = getExtension(file.getOriginalFilename());
      String storedName = UUID.randomUUID() + ext;
      Path target = dir.resolve(storedName);
      file.transferTo(target.toFile());

      return "/uploads/" + storedName; // 저장 경로(URL)
    } catch (IOException e) {
      throw new GeneralExeption(VerificationErrorCode.FILE_STORE_FAILED);
    }
  }

  private String getExtension(String filename) {
    if (filename == null || !filename.contains(".")) return "";
    return filename.substring(filename.lastIndexOf("."));
  }
}
