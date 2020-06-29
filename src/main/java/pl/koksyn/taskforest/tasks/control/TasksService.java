package pl.koksyn.taskforest.tasks.control;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.koksyn.taskforest.Clock;
import pl.koksyn.taskforest.exceptions.NotFoundException;
import pl.koksyn.taskforest.tasks.boundary.StorageService;
import pl.koksyn.taskforest.tasks.boundary.TasksRepository;
import pl.koksyn.taskforest.tasks.entity.Task;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class TasksService {
    private final StorageService storageService;
    private final TasksRepository tasksRepository;
    private final Clock clock;

    public List<Task> getAll() {
        return tasksRepository.getAll();
    }

    public List<Task> filterAll(String query) {
        return getAll()
                .stream()
                .filter(task -> task.getTitle().contains(query) || task.getDescription().contains(query))
                .collect(toList());
    }

    public Task get(long id) {
        return tasksRepository.get(id);
    }

    public Task addTask(String title, String description, String author) {
        Task task = new Task(
                title,
                description,
                author,
                clock.time()
        );

        tasksRepository.add(task);
        return task;
    }

    public void addTaskWithAttachment(String title, String description, String author, @NonNull MultipartFile attachment) throws IOException {
        Task task = addTask(title, description, author);
        String originalFilename = attachment.getOriginalFilename();

        if(!attachment.isEmpty() && StringUtils.isNotBlank(originalFilename)) {
            storageService.saveFile(task.getId(), attachment);
            task.addAttachment(originalFilename);
        }
    }

    public void updateTask(long id, String title, String description, String author) {
        tasksRepository.update(id, title, description, author);
    }

    public void deleteTask(long id) {
        Task task = get(id);
        task.getAttachments()
                .forEach(fileName -> {
                    storageService.deleteFileByNameIfExists(fileName);
                    task.removeAttachment(fileName);
                });

        tasksRepository.delete(id);
    }

    public void addAttachmentToTaskById(@NonNull MultipartFile attachment, long taskId) throws IOException {
        final String originalFilename = attachment.getOriginalFilename();

        if(!attachment.isEmpty() && StringUtils.isNotBlank(originalFilename)) {
            Task task = get(taskId);
            task.addAttachment(originalFilename);

            try {
                storageService.saveFile(taskId, attachment);
            } catch (IOException exception) {
                task.removeAttachment(originalFilename);
                throw exception;
            }
        }
    }

    public Resource getAttachmentFromTaskById(String filename, long taskId) throws MalformedURLException {
        Task task = get(taskId);
        if(!task.containsAttachment(filename)) {
            throw new NotFoundException("Task does not contain attachment with fileName: " + filename);
        }

        return storageService.loadFile(filename);
    }
}
