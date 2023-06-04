package y88.kirill.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import y88.kirill.dao.DaoPerson;
import y88.kirill.dto.PersonD;

import java.util.List;

@RestController
@RequestMapping("/person")
public class PersonController {

    private final DaoPerson daoPerson;

    public PersonController(DaoPerson daoPerson) {
        this.daoPerson = daoPerson;
    }

    @GetMapping
    public String getHello(){
        return "hello";
    }


    @GetMapping("/all")
    public List<PersonD> getAllPerson(){
        return daoPerson.getAllPerson();
    }


    @GetMapping("/by-name")
    public PersonD getPersonByName(@RequestParam String name){
        return daoPerson.getPersonByName(name);
    }

    @GetMapping("/by-project-title")
    public List<PersonD> getPersonByProjectTitle(@RequestParam String title){
        return daoPerson.getAllByProjectTitle(title);
    }

    @GetMapping("/by-position-title")
    public List<PersonD> getPersonByPositionTitle(@RequestParam String title){
        return daoPerson.getAllByPositionTitle(title);
    }


    @PostMapping("/create")
    public HttpStatus createPerson(@RequestParam String newPersonName,
                                   @RequestParam Long idPosition){
        boolean result = daoPerson.createNewPerson(newPersonName,idPosition);
        if(result){
            return HttpStatus.CREATED;
        }else {
            return HttpStatus.BAD_GATEWAY;
        }
    }

    @PostMapping("/updateName")
    public HttpStatus updatePersonName(@RequestParam String oldName,
                                     @RequestParam String newName){
        boolean result = daoPerson.updatePersonName(oldName, newName);
        if(result){
            return  HttpStatus.valueOf(200);
        }else {
            return  HttpStatus.I_AM_A_TEAPOT;
        }
    }

    @PostMapping("/updatePosition")
    public HttpStatus updatePersonPosition(@RequestParam String name,
                                       @RequestParam String position){
        boolean result = daoPerson.updatePersonPosition(name, position);
        if(result){
            return  HttpStatus.valueOf(200);
        }else {
            return  HttpStatus.I_AM_A_TEAPOT;
        }
    }

    @DeleteMapping("/delete")
    public HttpStatus deletePerson(String name){
        boolean result = daoPerson.deletePerson(name);
        if(result){
            return HttpStatus.valueOf(200);
        }else {
            return HttpStatus.I_AM_A_TEAPOT;
        }
    }



}
