package khasanshin.iblab1.controller;

import jakarta.validation.Valid;
import khasanshin.iblab1.dto.TaskDTO;
import khasanshin.iblab1.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.owasp.encoder.Encode;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

    @GetMapping(value = "/{id}/render", produces = MediaType.TEXT_HTML_VALUE)
    public ResponseEntity<String> render(@PathVariable Long id) {
        var dto = service.findById(id);
        String safeTitle = Encode.forHtmlContent(dto.getTitle());
        String safeDesc  = dto.getDescription() == null ? "" : Encode.forHtmlContent(dto.getDescription());

        String html = String.format("""
        <!doctype html>
        <html lang="en">
          <head>
            <meta charset="utf-8">
            <meta http-equiv="Content-Security-Policy" content="default-src 'self'; script-src 'self'; object-src 'none'; base-uri 'self'; frame-ancestors 'none'">
            <title>Task Preview</title>
          </head>
          <body>
            <h2>%s</h2>
            <p>%s</p>
          </body>
        </html>
        """, safeTitle, safeDesc);


        return ResponseEntity.ok().contentType(MediaType.TEXT_HTML).body(html);
    }

}
