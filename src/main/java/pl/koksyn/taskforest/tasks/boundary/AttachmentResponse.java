package pl.koksyn.taskforest.tasks.boundary;

import lombok.AllArgsConstructor;
import lombok.Data;
import pl.koksyn.taskforest.tasks.entity.Attachment;

@Data
@AllArgsConstructor
public class AttachmentResponse {
    private String fileName;
    private String comment;

    static AttachmentResponse from(Attachment attachment) {
        return new AttachmentResponse(
                attachment.getFileName(),
                attachment.getComment()
        );
    }
}
