package pl.koksyn.taskforest.tasks.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class Task {
    private long id;
    private String title;
    private String description;
    private String author;
    private LocalDateTime createdAt = LocalDateTime.now();
}
