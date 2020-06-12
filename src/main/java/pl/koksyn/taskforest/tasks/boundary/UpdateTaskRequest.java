package pl.koksyn.taskforest.tasks.boundary;

import lombok.Data;

@Data
public class UpdateTaskRequest {
    private String title;
    private String description;
    private String author;
}
