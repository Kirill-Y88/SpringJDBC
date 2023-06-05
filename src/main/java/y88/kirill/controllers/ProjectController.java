package y88.kirill.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import y88.kirill.dto.ProjectDto;
import y88.kirill.exceptions.ExceptionInfo;
import y88.kirill.services.ProjectService;

import java.util.List;

@RestController
@RequestMapping("/project")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @GetMapping("/all")
    public List<ProjectDto> getAllPosition() {
        return projectService.getAll();
    }

    @GetMapping("/by-id")
    public ResponseEntity<?> getProjectById(@RequestParam Long id) {
        try {
            return ResponseEntity.ok(projectService.getById(id));
        } catch (ExceptionInfo e) {
            return ResponseEntity.status(400).body(e);
        }
    }

    @GetMapping("/by-title")
    public ResponseEntity<?> getProjectByTitle(@RequestParam String title) {
        try {
            return ResponseEntity.ok(projectService.getByTitle(title));
        } catch (ExceptionInfo e) {
            return ResponseEntity.status(400).body(e);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestParam String title) {
        try {
            projectService.create(title);
            return ResponseEntity.ok().build();
        } catch (ExceptionInfo e) {
            return ResponseEntity.status(400).body(e);
        }
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateTitle(@RequestParam String oldTitle,
                                         @RequestParam String newTitle) {
        try {
            projectService.updateTitle(oldTitle, newTitle);
            return ResponseEntity.ok().build();
        } catch (ExceptionInfo e) {
            return ResponseEntity.status(400).body(e);
        }
    }

    @PostMapping("/update-add-person")
    public ResponseEntity<?> updateAddPerson(@RequestParam String titleProject,
                                             @RequestParam String namePerson) {
        try {
            projectService.updateAddPerson(titleProject, namePerson);
            return ResponseEntity.ok().build();
        } catch (ExceptionInfo e) {
            return ResponseEntity.status(400).body(e);
        }
    }

    @PostMapping("/update-remove-person")
    public ResponseEntity<?> updateRemovePerson(@RequestParam String titleProject,
                                                @RequestParam String namePerson) {
        try {
            projectService.updateRemovePerson(titleProject, namePerson);
            return ResponseEntity.ok().build();
        } catch (ExceptionInfo e) {
            return ResponseEntity.status(400).body(e);
        }
    }

    @DeleteMapping("/delete-by-title")
    public ResponseEntity<?> deleteByTitle(String title) {
        try {
            projectService.deleteByTitle(title);
            return ResponseEntity.ok().build();
        } catch (ExceptionInfo e) {
            return ResponseEntity.status(400).body(e);
        }
    }

    @DeleteMapping("/delete-by-id")
    public ResponseEntity<?> deleteById(Long id) {
        try {
            projectService.deleteById(id);
            return ResponseEntity.ok().build();
        } catch (ExceptionInfo e) {
            return ResponseEntity.status(400).body(e);
        }
    }


}
