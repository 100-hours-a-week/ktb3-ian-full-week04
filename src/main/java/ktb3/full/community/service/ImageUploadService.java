package ktb3.full.community.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class ImageUploadService {

    @Value("${file.path.base}")
    private String fileBasePath;

    @Value("${file.path.image}")
    private String fileImagePath;

    public String saveImageAndGetPath(MultipartFile image) throws IOException {
        String imagePath = null;

        if (image != null) {
            imagePath = this.fileImagePath + "/" + UUID.randomUUID() + image.getOriginalFilename();
            Path path = Paths.get(this.fileBasePath + imagePath);
            Files.createDirectories(path.getParent());
            Files.write(path, image.getBytes());
        }

        return imagePath;
    }
}
