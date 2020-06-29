package pl.koksyn.taskforest.tasks.boundary;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.koksyn.taskforest.tasks.control.TasksService;

import java.io.IOException;

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
    public String addTask(
            @ModelAttribute("newTask") CreateTaskRequest request,
            @RequestParam("attachment") MultipartFile attachment
    ) throws IOException {
        tasksService.addTaskWithAttachment(request.getTitle(), request.getDescription(), request.getAuthor(), attachment, request.getAttachmentComment());
        return "redirect:/";
    }

    @PostMapping("/tasks/delete/{id}")
    public String deleteTask(@PathVariable Long id) {
        tasksService.deleteTask(id);
        return "redirect:/";
    }
}
