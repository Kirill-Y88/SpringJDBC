package y88.kirill.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import y88.kirill.dto.PositionDto;
import y88.kirill.exceptions.ExceptionInfo;
import y88.kirill.services.PositionService;

import java.util.List;


@RestController
@RequestMapping("/position")
@RequiredArgsConstructor
public class PositionController {

    private final PositionService positionService;

    @GetMapping("/all")
    public List<PositionDto> getAllPosition() {
        return positionService.getAll();
    }

    @GetMapping("/by-id")
    public ResponseEntity<?> getPositionById(@RequestParam Long id) {
        try {
            return ResponseEntity.ok(positionService.getById(id));
        } catch (ExceptionInfo e) {
            return ResponseEntity.status(400).body(e);
        }
    }

    @GetMapping("/by-title")
    public ResponseEntity<?> getPositionByTitle(@RequestParam String title) {
        try {
            return ResponseEntity.ok(positionService.getByTitle(title));
        } catch (ExceptionInfo e) {
            return ResponseEntity.status(400).body(e);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> createPosition(@RequestParam String title) {
        try {
            positionService.create(title);
            return ResponseEntity.ok().build();
        } catch (ExceptionInfo e) {
            return ResponseEntity.status(400).body(e);
        }
    }

    @PostMapping("/update")
    public ResponseEntity<?> updatePositionTitle(@RequestParam String oldTitle,
                                              @RequestParam String newTitle) {
        try {
            positionService.updateTitle(oldTitle, newTitle);
            return ResponseEntity.ok().build();
        } catch (ExceptionInfo e) {
            return ResponseEntity.status(400).body(e);
        }
    }

    @DeleteMapping("/delete-by-title")
    public ResponseEntity<?> deleteByTitle(String title) {
        try {
            positionService.deleteByTitle(title);
            return ResponseEntity.ok().build();
        } catch (ExceptionInfo e) {
            return ResponseEntity.status(400).body(e);
        }
    }

    @DeleteMapping("/delete-by-id")
    public ResponseEntity<?> deleteById(Long id) {
        try {
            positionService.deleteById(id);
            return ResponseEntity.ok().build();
        } catch (ExceptionInfo e) {
            return ResponseEntity.status(400).body(e);
        }
    }

}
