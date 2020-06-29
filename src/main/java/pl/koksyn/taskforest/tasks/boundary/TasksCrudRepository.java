package pl.koksyn.taskforest.tasks.boundary;

import org.springframework.data.repository.CrudRepository;
import pl.koksyn.taskforest.tasks.entity.Task;

interface TasksCrudRepository extends CrudRepository<Task, Long> {
}
