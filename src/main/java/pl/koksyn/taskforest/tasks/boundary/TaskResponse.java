package pl.koksyn.taskforest.tasks.boundary;

import lombok.AllArgsConstructor;
import lombok.Data;
import pl.koksyn.taskforest.tasks.entity.Task;

import java.time.LocalDateTime;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

@Data
@AllArgsConstructor
public class TaskResponse {
    private long id;
    private String title;
    private String description;
    private String author;
    private LocalDateTime createdAt;
    private Set<AttachmentResponse> attachments;

    static TaskResponse from(Task task) {
        return new TaskResponse(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getAuthor(),
                task.getCreatedAt(),
                task.getAttachments()
                        .stream()
                        .map(AttachmentResponse::from)
                        .collect(toSet())
        );
    }
}
