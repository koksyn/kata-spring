package pl.koksyn.taskforest.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "taskforest.tasks.storage.filesystem")
public class FileSystemStorageConfig {
    private String targetDir;
}