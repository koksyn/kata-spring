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

    @GetMapping("/{id}")
    public Task get(@PathVariable long id) {
        log.info("Fetching task by id: {} ...", id);
        return tasksRepository.get(id);
    }

    @PostMapping
    public void add(@RequestBody Task task) {
        log.info("Storing new task {}", task);
        tasksRepository.add(task);
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
}
