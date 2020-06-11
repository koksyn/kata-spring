package pl.koksyn.taskforest.tasks;

import java.util.List;

public interface TasksRepository {
    void add(Task task);
    List<Task> getAll();

    Task get(long id);

    void delete(long id);
}
