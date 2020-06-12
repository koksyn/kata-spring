package pl.koksyn.taskforest.tasks.boundary;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface StorageService {
    void saveFile(long taskId, MultipartFile file) throws IOException;
}
