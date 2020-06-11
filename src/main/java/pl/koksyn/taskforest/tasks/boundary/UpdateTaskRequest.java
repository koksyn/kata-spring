package pl.koksyn.taskforest.tasks.boundary;

import lombok.Data;

@Data
public class UpdateTaskRequest {
    String title;
    String description;
    String author;
}
