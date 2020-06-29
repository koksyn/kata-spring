package pl.koksyn.taskforest.tasks.boundary;

import pl.koksyn.taskforest.tasks.entity.Task;

import java.util.List;

public interface TasksRepository {
    List<Task> getAll();
    Task get(long id);
    void add(Task task);
    void update(long id, String title, String description, String author);
    void delete(long id);
    void save(Task task);
}
