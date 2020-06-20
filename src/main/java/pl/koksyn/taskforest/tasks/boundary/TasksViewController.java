package pl.koksyn.taskforest.tasks.boundary;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.koksyn.taskforest.tasks.control.TasksService;

@Controller
@RequiredArgsConstructor
public class TasksViewController {
    private final TasksService tasksService;

    @GetMapping("/")
    public String hello(Model model) {
        model.addAttribute("tasks", tasksService.getAll());

        return "home";
    }
}
