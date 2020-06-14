package pl.koksyn.taskforest.tasks.boundary;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.koksyn.taskforest.tasks.control.TasksService;
import pl.koksyn.taskforest.tasks.entity.Task;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Slf4j
@RestController
@RequestMapping(path = "/api/tasks")
@RequiredArgsConstructor
public class TasksController {
    private final TasksService tasksService;

    @PostConstruct
    void init() {
        log.info("Adding example Tasks...");

        tasksService.addTask("Zetnij drzewo", "Siekiera", "Drwal");
        tasksService.addTask("Posadz drzewo", "Lopata", "Ogrodnik");
        tasksService.addTask("Podlewaj drzewo", "Konewka", "Ogrodnik");
    }

    @GetMapping
    public List<TaskResponse> get(@RequestParam Optional<String> query) {
        log.info("Getting all Tasks with query: {}", query);

        return query.map(tasksService::filterAll)
                .orElseGet(tasksService::getAll)
                .stream()
                .map(this::toTaskResponse)
                .collect(toList());
    }

    @GetMapping("/{id}")
    public TaskResponse get(@PathVariable long id) {
        log.info("Getting Task by id: {}", id);

        return toTaskResponse(tasksService.get(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void add(@RequestBody CreateTaskRequest request) {
        log.info("Adding new Task {}", request);

        tasksService.addTask(request.getTitle(), request.getDescription(), request.getAuthor());
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable long id, @RequestBody UpdateTaskRequest request) {
        log.info("Updating Task by id: {}", id);

        tasksService.updateTask(id, request.getTitle(), request.getDescription(), request.getAuthor());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {
        log.info("Deleting task by id: {}", id);

        tasksService.deleteTask(id);
    }

    @GetMapping("/{id}/attachments/{filename}")
    public ResponseEntity getAttachment(
            @PathVariable long id,
            @PathVariable String filename,
            HttpServletRequest request) throws IOException {
        log.info("Getting attachment '{}' from Task by id: {}", filename, id);

        Resource resource = tasksService.getAttachmentFromTaskById(filename, id);

        String absolutePath = resource.getFile().getAbsolutePath();
        String mimeType = request.getServletContext().getMimeType(absolutePath);
        MediaType contentType = (StringUtils.isBlank(mimeType)) ?
                MediaType.APPLICATION_OCTET_STREAM :
                MediaType.parseMediaType(mimeType);

        return ResponseEntity.ok()
                .contentType(contentType)
                .body(resource);
    }

    @PostMapping("/{id}/attachments")
    @ResponseStatus(HttpStatus.CREATED)
    public void addAttachment(@PathVariable long id, @RequestParam("file") MultipartFile file) throws IOException {
        log.info("Adding attachment: '{}' to Task by id: {}", file.getName(), id);

        tasksService.addAttachmentToTaskById(file, id);
    }

    private TaskResponse toTaskResponse(@NonNull Task task) {
        return new TaskResponse(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getAuthor(),
                task.getCreatedAt(),
                task.getAttachments()
        );
    }
}
