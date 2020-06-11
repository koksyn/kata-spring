package pl.koksyn.taskforest.tasks.control;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.koksyn.taskforest.tasks.boundary.TasksRepository;
import pl.koksyn.taskforest.tasks.entity.Task;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicLong;

@Service
@RequiredArgsConstructor
public class TasksService {
    private final TasksRepository tasksRepository;
    private final AtomicLong nextTaskId = new AtomicLong(0L);

    public void addTask(String title, String description, String author) {
        tasksRepository.add(
                new Task(
                        nextTaskId.getAndIncrement(),
                        title,
                        description,
                        author,
                        LocalDateTime.now()
                )
        );
    }
}
