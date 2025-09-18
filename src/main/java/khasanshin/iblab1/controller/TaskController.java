package khasanshin.iblab1.controller;

import jakarta.validation.Valid;
import khasanshin.iblab1.dto.TaskDTO;
import khasanshin.iblab1.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController @RequestMapping("/api/data") @RequiredArgsConstructor
public class TaskController {
    private final TaskService service;

    @GetMapping
    public List<TaskDTO> all() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public TaskDTO one(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TaskDTO create(@RequestBody @Valid TaskDTO dto) {
        return service.create(dto);
    }

    @PutMapping("/{id}")
    public TaskDTO update(@PathVariable Long id, @RequestBody @Valid TaskDTO dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
