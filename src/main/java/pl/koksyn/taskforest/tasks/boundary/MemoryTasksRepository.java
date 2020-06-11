package pl.koksyn.taskforest.tasks.boundary;

import org.springframework.stereotype.Component;
import pl.koksyn.taskforest.tasks.entity.Task;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
        return tasks.stream()
                .filter(task -> id == task.getId())
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Task not found"));
    }

    @Override
    public void delete(long id) {
        tasks.stream()
                .filter(task -> id == task.getId())
                .findFirst()
                .ifPresent(tasks::remove);
    }
}
