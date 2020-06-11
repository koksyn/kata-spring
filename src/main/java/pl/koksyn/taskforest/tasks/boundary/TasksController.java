package pl.koksyn.taskforest.tasks.boundary;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import pl.koksyn.taskforest.tasks.control.TasksService;
import pl.koksyn.taskforest.tasks.entity.Task;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Slf4j
@RestController
@RequestMapping(path = "/api/tasks")
@RequiredArgsConstructor
public class TasksController {
    private final TasksService tasksService;
    private final TasksRepository tasksRepository;

    @PostConstruct
    void init() {
        tasksService.addTask("Zetnij drzewo", "Siekiera", "Drwal");
        tasksService.addTask("Posadz drzewo", "Lopata", "Ogrodnik");
        tasksService.addTask("Podlewaj drzewo", "Konewka", "Ogrodnik");
    }

    @GetMapping
    public List<TaskResponse> get() {
        log.info("Fetching all tasks...");
        return tasksRepository.getAll()
                .stream()
                .map(this::toTaskResponse)
                .collect(toList());
    }

    @GetMapping("/{id}")
    public TaskResponse get(@PathVariable long id) {
        log.info("Fetching task by id: {} ...", id);
        return toTaskResponse(tasksRepository.get(id));
    }

    @PostMapping
    public void add(@RequestBody CreateTaskRequest task) {
        log.info("Storing new task {}", task);
        tasksService.addTask(task.getTitle(), task.getDescription(), task.getAuthor());
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        log.info("Deleting task by id: {} ...", id);
        tasksRepository.delete(id);
    }

    @PutMapping("/{id}")
    public void update(@PathVariable long id) {
        log.info("Updating task by id: {} ...", id);
//        tasksRepository.update(id);
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
