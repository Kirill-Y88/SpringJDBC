package y88.kirill.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import y88.kirill.dao.DaoPerson;
import y88.kirill.dao.DaoPosition;
import y88.kirill.dto.Position;
import y88.kirill.dto.Project;

import java.util.List;


@RestController
@RequestMapping("/position")
public class PositionController {

    private final DaoPosition daoPosition;
    private final DaoPerson daoPerson;

    public PositionController(DaoPosition daoPosition, DaoPerson daoPerson) {
        this.daoPosition = daoPosition;
        this.daoPerson = daoPerson;
    }

    @GetMapping("/{id}")
    public Position getPositionById(@PathVariable Long id){
        return daoPosition.getPositionById(id);
    }

    @GetMapping("/by-title")
    public Position getPositionByTitle(@RequestParam String title){
        return daoPosition.getPositionByTitle(title);
    }

    @GetMapping("/all")
    public List<Position> getAllPosition(){
        return daoPosition.getAllPosition();
    }

    @PostMapping("/create")
    public HttpStatus createPosition(@RequestParam String newPositionName){
        boolean result = daoPosition.createNewPosition(newPositionName);
        if(result){
            return HttpStatus.CREATED;
        }else {
            return HttpStatus.BAD_GATEWAY;
        }
    }

    @PostMapping("/update")
    public HttpStatus updatePosition(@RequestParam String oldTitle,
                                            @RequestParam String newTitle){
        boolean result = daoPosition.updatePosition(oldTitle, newTitle);
        if(result){
            return  HttpStatus.valueOf(200);
        }else {
            return  HttpStatus.I_AM_A_TEAPOT;
        }
    }

    @DeleteMapping("/delete")
    public HttpStatus deletePosition(String title){
        boolean result = daoPosition.deletePosition(title);
        if(result){
            return HttpStatus.valueOf(200);
        }else {
            return HttpStatus.I_AM_A_TEAPOT;
        }
    }


}
