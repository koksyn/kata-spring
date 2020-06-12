package pl.koksyn.taskforest.tasks.control;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.koksyn.taskforest.Clock;
import pl.koksyn.taskforest.tasks.boundary.TasksRepository;
import pl.koksyn.taskforest.tasks.entity.Task;
import java.util.concurrent.atomic.AtomicLong;

@Service
@RequiredArgsConstructor
public class TasksService {
    private final TasksRepository tasksRepository;
    private final Clock clock;
    private final AtomicLong nextTaskId = new AtomicLong(0L);

    public void addTask(String title, String description, String author) {
        tasksRepository.add(
                new Task(
                        nextTaskId.getAndIncrement(),
                        title,
                        description,
                        author,
                        clock.time()
                )
        );
    }

    public void updateTask(long id, String title, String description, String author) {
        tasksRepository.update(id, title, description, author);
    }
}
