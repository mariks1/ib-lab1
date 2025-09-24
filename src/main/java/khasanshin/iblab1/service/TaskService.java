package khasanshin.iblab1.service;

import jakarta.persistence.EntityNotFoundException;
import khasanshin.iblab1.dto.TaskDTO;
import khasanshin.iblab1.entity.Task;
import khasanshin.iblab1.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;

    @Transactional(readOnly = true)
    public List<TaskDTO> findAll() {
        return taskRepository.findAll().stream()
                .map(t -> new TaskDTO(t.getId(), t.getTitle(), t.getDescription()))
                .toList();
    }

    @Transactional(readOnly = true)
    public TaskDTO findById(Long id) {
        Task t = taskRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Task not found"));
        return new TaskDTO(t.getId(), t.getTitle(), t.getDescription());
    }

    @Transactional
    public TaskDTO create(TaskDTO dto) {
        Task saved = taskRepository.save(Task.builder().title(dto.getTitle()).description(dto.getDescription()).build());
        return new TaskDTO(saved.getId(), saved.getTitle(), saved.getDescription());
    }

    @Transactional
    public TaskDTO update(Long id, TaskDTO dto) {
        Task t = taskRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Task not found"));
        t.setTitle(dto.getTitle());
        t.setDescription(dto.getDescription());
        return new TaskDTO(t.getId(), t.getTitle(), t.getDescription());
    }

    @Transactional
    public void delete(Long id) {
        if (!taskRepository.existsById(id)) throw new EntityNotFoundException("Task not found");
        taskRepository.deleteById(id);
    }
}
