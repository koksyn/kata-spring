package pl.koksyn.taskforest.tasks.boundary;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import pl.koksyn.taskforest.exceptions.NotFoundException;
import pl.koksyn.taskforest.tasks.entity.Task;
import java.util.List;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.toUnmodifiableList;

@Primary
@Repository
@RequiredArgsConstructor
public class AdaptedTasksCrudRepository implements TasksRepository {
    private final TasksCrudRepository repository;

    @Override
    public List<Task> getAll() {
        return StreamSupport
                .stream(repository.findAll().spliterator(), false)
                .collect(toUnmodifiableList());
    }

    @Override
    public Task get(long id) {
        return repository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Task with id: " + id + " not found."));
    }

    @Override
    public void add(Task task) {
        repository.save(task);
    }

    @Override
    public void update(long id, String title, String description, String author) {
        Task task = get(id);

        task.setTitle(title);
        task.setDescription(description);
        task.setAuthor(author);

        repository.save(task);
    }

    @Override
    public void delete(long id) {
        repository.deleteById(id);
    }

    @Override
    public void save(Task task) {
        repository.save(task);
    }
}
