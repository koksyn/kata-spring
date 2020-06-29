package pl.koksyn.taskforest.tasks.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Set;

@Data
@Table("tasks")
public class Task {
    @Id
    private Long id;
    private String title;
    private String description;
    private String author;
    private LocalDateTime createdAt;

    public Task(String title, String description, String author, LocalDateTime createdAt) {
        this.title = title;
        this.description = description;
        this.author = author;
        this.createdAt = createdAt;
    }

    //private final Set<String> attachments = new HashSet<>();

    public Set<String> getAttachments() {
//        return Collections.unmodifiableSet(attachments);
        return Collections.emptySet();
    }

    public boolean containsAttachment(String fileName) {
//        return attachments.stream()
//                .anyMatch(attachment -> attachment.equals(fileName));
        return false;
    }

    public void addAttachment(String fileName) {
//        if(StringUtils.isBlank(fileName)) {
//            throw new IllegalArgumentException("Attachment fileName cannot be empty or blank.");
//        }
//        if(attachments.contains(fileName)) {
//            throw new AttachmentExistsException(String.format(
//                "Cannot add attachment, because filename '%s' is already added to this to Task(id=%s)", fileName, id
//            ));
//        }
//        attachments.add(fileName);
    }

    public void removeAttachment(String fileName) {
//        attachments.remove(fileName);
    }
}
