package pl.koksyn.taskforest.tasks.boundary;

import pl.koksyn.taskforest.tasks.entity.Task;

import java.util.List;

public interface TasksRepository {
    void add(Task task);
    List<Task> getAll();

    Task get(long id);

    void delete(long id);

    void update(long id, String title, String description, String author);
}
