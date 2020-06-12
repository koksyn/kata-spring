package pl.koksyn.taskforest.tasks.boundary;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@AllArgsConstructor
public class TaskResponse {
    private long id;
    private String title;
    private String description;
    private String author;
    private LocalDateTime createdAt;
    private Set<String> attachments;
}
