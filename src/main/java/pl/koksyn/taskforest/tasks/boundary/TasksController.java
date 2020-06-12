package pl.koksyn.taskforest.tasks.boundary;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.koksyn.taskforest.tasks.control.TasksService;
import pl.koksyn.taskforest.tasks.entity.Task;
import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Slf4j
@RestController
@RequestMapping(path = "/api/tasks")
@RequiredArgsConstructor
public class TasksController {
    private final StorageService storageService;
    private final TasksService tasksService;
    private final TasksRepository tasksRepository;

    @PostConstruct
    void init() {
        tasksService.addTask("Zetnij drzewo", "Siekiera", "Drwal");
        tasksService.addTask("Posadz drzewo", "Lopata", "Ogrodnik");
        tasksService.addTask("Podlewaj drzewo", "Konewka", "Ogrodnik");
    }

    @GetMapping
    public List<TaskResponse> get(@RequestParam Optional<String> query) {
        log.info("Fetching all tasks with query: {}", query);

        return query.map(tasksService::filterAll)
                .orElseGet(tasksService::getAll)
                .stream()
                .map(this::toTaskResponse)
                .collect(toList());
    }

    @GetMapping("/{id}")
    public TaskResponse get(@PathVariable long id) {
        log.info("Fetching task by id: {} ...", id);
        return toTaskResponse(tasksRepository.get(id));
    }

    @GetMapping("/{id}/attachments/{filename}")
    public ResponseEntity getAttachment(@PathVariable long id, @PathVariable String filename) {
        // pobierz plik
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/attachments")
    public ResponseEntity addAttachment(@PathVariable long id, @RequestParam("file") MultipartFile file) throws IOException {
        log.info("Handling file upload: {}", file.getName());
        storageService.saveFile(id, file);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public void add(@RequestBody CreateTaskRequest request) {
        log.info("Storing new task {}", request);
        tasksService.addTask(request.getTitle(), request.getDescription(), request.getAuthor());
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        log.info("Deleting task by id: {} ...", id);
        tasksRepository.delete(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@PathVariable long id, @RequestBody UpdateTaskRequest request) {
        log.info("Updating task by id: {} ...", id);
        tasksService.updateTask(id, request.getTitle(), request.getDescription(), request.getAuthor());
    }

    private TaskResponse toTaskResponse(Task task) {
        return new TaskResponse(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getAuthor(),
                task.getCreatedAt()
        );
    }
}
