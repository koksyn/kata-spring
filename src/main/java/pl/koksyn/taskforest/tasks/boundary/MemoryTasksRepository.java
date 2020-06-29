package pl.koksyn.taskforest.tasks.boundary;

import lombok.NonNull;
import org.springframework.stereotype.Repository;
import pl.koksyn.taskforest.exceptions.NotFoundException;
import pl.koksyn.taskforest.tasks.entity.Task;
import java.util.*;

@Repository
public class MemoryTasksRepository implements TasksRepository {
    private final Set<Task> tasks = new HashSet<>();

    @Override
    public List<Task> getAll() {
        return new ArrayList<>(tasks);
    }

    @Override
    public Task get(long id) {
        return findById(id).orElseThrow(() -> new NotFoundException("Task with id: " + id + " not found."));
    }

    @Override
    public void add(@NonNull Task task) {
        tasks.add(task);
    }

    @Override
    public void update(long id, String title, String description, String author) {
        Task task = get(id);
        task.setTitle(title);
        task.setDescription(description);
        task.setAuthor(author);
    }

    @Override
    public void delete(long id) {
        findById(id).ifPresent(tasks::remove);
    }

    private Optional<Task> findById(long id) {
        return tasks.stream()
                .filter(task -> id == task.getId())
                .findFirst();
    }
}
