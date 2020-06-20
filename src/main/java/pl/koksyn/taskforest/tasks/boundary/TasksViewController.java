package pl.koksyn.taskforest.tasks.boundary;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import pl.koksyn.taskforest.tasks.control.TasksService;

@Controller
@RequiredArgsConstructor
public class TasksViewController {
    private final TasksService tasksService;

    @GetMapping("/")
    public String hello(Model model) {
        model.addAttribute("tasks", tasksService.getAll());
        model.addAttribute("newTask", new CreateTaskRequest());
        return "home";
    }

    @PostMapping("/tasks")
    public String addTask(@ModelAttribute("newTask") CreateTaskRequest request) {
        tasksService.addTask(request.getTitle(), request.getDescription(), request.getAuthor());
        return "redirect:/";
    }

    @PostMapping("/tasks/delete/{id}")
    public String deleteTask(@PathVariable Long id) {
        tasksService.deleteTask(id);
        return "redirect:/";
    }
}
