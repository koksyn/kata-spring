package pl.koksyn.taskforest.tasks.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import pl.koksyn.taskforest.exceptions.AttachmentExistsException;
import pl.koksyn.taskforest.exceptions.NotFoundException;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
public class Task {
    private long id;
    private String title;
    private String description;
    private String author;
    private LocalDateTime createdAt;

    private final Set<String> attachments = new HashSet<>();

    public Set<String> getAttachments() {
        return Collections.unmodifiableSet(attachments);
    }

    public boolean containsAttachment(String fileName) {
        return attachments.stream()
                .anyMatch(attachment -> attachment.equals(fileName));
    }

    public void addAttachment(String fileName) {
        if(StringUtils.isBlank(fileName)) {
            throw new IllegalArgumentException("Attachment fileName cannot be empty or blank.");
        }
        if(attachments.contains(fileName)) {
            throw new AttachmentExistsException(String.format(
                "Cannot add attachment, because filename '%s' is already added to this to Task(id=%s)", fileName, id
            ));
        }
        attachments.add(fileName);
    }

    public void removeAttachment(String fileName) {
        attachments.remove(fileName);
    }
}
