package pl.koksyn.taskforest.tasks.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("attachments")
@AllArgsConstructor
public class Attachment {
    private String fileName;
    private String comment;
}
