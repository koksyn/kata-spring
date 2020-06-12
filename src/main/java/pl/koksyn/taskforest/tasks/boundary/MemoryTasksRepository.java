package pl.koksyn.taskforest.tasks.boundary;

import org.springframework.stereotype.Component;
import pl.koksyn.taskforest.exceptions.NotFoundException;
import pl.koksyn.taskforest.tasks.entity.Task;

import java.util.*;

@Component
public class MemoryTasksRepository implements TasksRepository {
    private final Set<Task> tasks = new HashSet<>();

    @Override
    public void add(Task task) {
        tasks.add(task);
    }

    @Override
    public List<Task> getAll() {
        return new ArrayList<>(tasks);
    }

    @Override
    public Task get(long id) {
        return findById(id).orElseThrow(() -> new IllegalArgumentException("Task not found"));
    }

    @Override
    public void delete(long id) {
        findById(id).ifPresent(tasks::remove);
    }

    @Override
    public void update(long id, String title, String description, String author) {
        Task task = findById(id)
                .orElseThrow(() -> new NotFoundException("Task with id: " + id + " not found."));
        task.setTitle(title);
        task.setDescription(description);
        task.setAuthor(author);
    }

    private Optional<Task> findById(long id) {
        return tasks.stream()
                .filter(task -> id == task.getId())
                .findFirst();
    }
}
