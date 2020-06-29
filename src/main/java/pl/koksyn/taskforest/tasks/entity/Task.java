package pl.koksyn.taskforest.tasks.entity;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import pl.koksyn.taskforest.exceptions.AttachmentExistsException;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static java.util.stream.Collectors.toUnmodifiableSet;

@Data
@Table("tasks")
public class Task {
    @Id
    private Long id;
    private String title;
    private String description;
    private String author;
    private LocalDateTime createdAt;
    private Set<Attachment> attachments = new HashSet<>();

    public Task(String title, String description, String author, LocalDateTime createdAt) {
        this.title = title;
        this.description = description;
        this.author = author;
        this.createdAt = createdAt;
    }

    public Set<String> getAttachments() {
        return attachments.stream()
                .map(Attachment::getFileName)
                .collect(toUnmodifiableSet());
    }

    public boolean containsAttachment(String fileName) {
        return attachments.stream()
                .anyMatch(attachment -> fileName.equals(attachment.getFileName()));
    }

    public void addAttachment(String fileName, String comment) {
        if(StringUtils.isBlank(fileName)) {
            throw new IllegalArgumentException("Attachment fileName cannot be empty or blank.");
        }
        if(containsAttachment(fileName)) {
            throw new AttachmentExistsException(String.format(
                "Cannot add attachment, because filename '%s' is already added to this to Task(id=%s)", fileName, id
            ));
        }
        attachments.add(new Attachment(fileName, comment));
    }

    public void removeAttachment(String fileName) {
        attachments.removeIf(attachment -> fileName.equals(attachment.getFileName()));
    }
}
