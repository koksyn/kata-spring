package pl.koksyn.taskforest.tasks.boundary;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@RequiredArgsConstructor
public class FileSystemStorageService implements StorageService {
    private final Path targetDirPath;

    @PostConstruct
    private void prepare() throws IOException {
        if(Files.notExists(targetDirPath)) {
            Files.createDirectory(targetDirPath);
        }
    }

    @Override
    public void saveFile(long taskId, MultipartFile file) throws IOException {
        final String fileName = file.getName();
        Path targetFilePath = targetDirPath.resolve(fileName);
        Files.copy(file.getInputStream(), targetFilePath, StandardCopyOption.REPLACE_EXISTING);
    }

    @Override
    public Resource loadFile(String filename) throws MalformedURLException {
        return new UrlResource(targetDirPath.resolve(filename).toUri());
    }
}
