package pl.koksyn.taskforest.tasks.boundary;

import lombok.Data;

@Data
public class CreateTaskRequest {
    String title;
    String description;
    String author;
}
