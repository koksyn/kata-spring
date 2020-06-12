package pl.koksyn.taskforest.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.koksyn.taskforest.Clock;
import pl.koksyn.taskforest.SystemClock;
import pl.koksyn.taskforest.tasks.boundary.FileSystemStorageService;
import pl.koksyn.taskforest.tasks.boundary.StorageService;
import java.nio.file.Path;

@Slf4j
@Configuration
public class TaskForestConfig {
    @Bean
    public Clock clock() {
        log.info("Clock as spring-bean registration...");
        return new SystemClock();
    }

    @Bean
    public StorageService storageService(FileSystemStorageConfig fileSystemStorageConfig) {
        final Path targetDirPath = Path.of(fileSystemStorageConfig.getTargetDir());
        return new FileSystemStorageService(targetDirPath);
    }
}
