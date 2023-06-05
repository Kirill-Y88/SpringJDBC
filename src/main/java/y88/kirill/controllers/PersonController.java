package y88.kirill.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import y88.kirill.dto.PersonDto;
import y88.kirill.exceptions.ExceptionInfo;
import y88.kirill.services.PersonService;

import java.util.List;


@RestController
@RequestMapping("/person")
@RequiredArgsConstructor
public class PersonController {

    private final PersonService personService;


    @GetMapping("/all")
    public List<PersonDto> getAllPerson() {
        return personService.getAll();
    }

    @GetMapping("/by-id")
    public ResponseEntity<?> getPersonById(@RequestParam Long id) {
        try {
            return ResponseEntity.ok(personService.getById(id));
        } catch (ExceptionInfo e) {
            return ResponseEntity.status(400).body(e);
        }
    }

    @GetMapping("/by-name")
    public ResponseEntity<?> getPersonByName(@RequestParam String name) {
        try {
            return ResponseEntity.ok(personService.getByName(name));
        } catch (ExceptionInfo e) {
            return ResponseEntity.status(400).body(e);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> createPerson(@RequestParam String name,
                                          @RequestParam Long idPosition) {
        try {
            personService.create(name, idPosition);
            return ResponseEntity.ok().build();
        } catch (ExceptionInfo e) {
            return ResponseEntity.status(400).body(e);
        }
    }

    @PostMapping("/update-name")
    public ResponseEntity<?> updatePersonName(@RequestParam String oldName,
                                              @RequestParam String newName) {
        try {
            personService.updateName(oldName, newName);
            return ResponseEntity.ok().build();
        } catch (ExceptionInfo e) {
            return ResponseEntity.status(400).body(e);
        }
    }

    @PostMapping("/update-position")
    public ResponseEntity<?> updatePersonPosition(@RequestParam String name,
                                                  @RequestParam String position) {
        try {
            personService.updatePosition(name, position);
            return ResponseEntity.ok().build();
        } catch (ExceptionInfo e) {
            return ResponseEntity.status(400).body(e);
        }
    }

    @DeleteMapping("/delete-by-name")
    public ResponseEntity<?> deleteByName(String name) {
        try {
            personService.deleteByName(name);
            return ResponseEntity.ok().build();
        } catch (ExceptionInfo e) {
            return ResponseEntity.status(400).body(e);
        }
    }

    @DeleteMapping("/delete-by-id")
    public ResponseEntity<?> deleteByPerson(Long id) {
        try {
            personService.deleteById(id);
            return ResponseEntity.ok().build();
        } catch (ExceptionInfo e) {
            return ResponseEntity.status(400).body(e);
        }
    }

}
