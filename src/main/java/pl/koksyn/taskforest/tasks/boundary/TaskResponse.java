package pl.koksyn.taskforest.tasks.boundary;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class TaskResponse {
    private long id;
    private String title;
    private String description;
    private String author;
    private LocalDateTime createdAt;
}
