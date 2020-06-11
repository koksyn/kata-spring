package pl.koksyn.taskforest.tasks;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/api/tasks")
@RequiredArgsConstructor
public class TasksController {
    private final TasksRepository tasksRepository;

    @GetMapping
    public List<Task> get() {
        log.info("Fetching all tasks...");
        return tasksRepository.getAll();
    }

    @PostMapping
    public void add(@RequestBody Task task) {
        log.info("Storing new task {}", task);
        tasksRepository.add(task);
    }

    @DeleteMapping
    public void delete() {
        log.info("Deleting task");
    }

    @PutMapping
    public void update() {
        log.info("Updating task");
    }
}
