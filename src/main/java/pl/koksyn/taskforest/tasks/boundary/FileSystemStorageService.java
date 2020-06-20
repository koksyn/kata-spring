package pl.koksyn.taskforest.tasks.boundary;

import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.util.Optional;

@RequiredArgsConstructor
public class FileSystemStorageService implements StorageService {
    private final Path targetDirPath;

    @Override
    public void saveFile(long taskId, MultipartFile source) throws IOException {
        final String originalFileName = Optional.ofNullable(source.getOriginalFilename())
                .orElseThrow(() -> new IllegalArgumentException("Cannot save file without filename."));
        File targetFile = targetDirPath.resolve(originalFileName).toFile();
        FileUtils.copyInputStreamToFile(source.getInputStream(), targetFile);
    }

    @Override
    public Resource loadFile(String filename) throws MalformedURLException {
        return new UrlResource(targetDirPath.resolve(filename).toUri());
    }

    @Override
    public void deleteFileByNameIfExists(String fileName) {
        File fileToDelete = targetDirPath.resolve(fileName).toFile();
        FileUtils.deleteQuietly(fileToDelete);
    }
}
