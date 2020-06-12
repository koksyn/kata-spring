package pl.koksyn.taskforest.tasks.control;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
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
    private final AtomicLong nextTaskId = new AtomicLong(0L);

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

    public void addTask(String title, String description, String author) {
        tasksRepository.add(
                new Task(
                        nextTaskId.getAndIncrement(),
                        title,
                        description,
                        author,
                        clock.time()
                )
        );
    }

    public void updateTask(long id, String title, String description, String author) {
        tasksRepository.update(id, title, description, author);
    }


    public void delete(long id) {
        tasksRepository.delete(id);
    }

    public void addAttachmentToTaskById(@NonNull MultipartFile file, long id) throws IOException {
        Task task = get(id);

        final String fileName = file.getName();
        task.addAttachment(fileName);

        try {
            storageService.saveFile(id, file);
        } catch (IOException exception) {
            task.removeAttachment(fileName);
            throw exception;
        }
    }

    public Resource getAttachmentFromTaskById(String filename, long id) throws MalformedURLException {
        Task task = get(id);
        if(!task.containsAttachment(filename)) {
            throw new NotFoundException("Task does not contain attachment with fileName: " + filename);
        }

        return storageService.loadFile(filename);
    }
}
