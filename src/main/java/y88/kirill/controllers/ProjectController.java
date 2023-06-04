package y88.kirill.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import y88.kirill.dao.DaoProject;
import y88.kirill.db.DbManager;
import y88.kirill.dto.ProjectD;

import javax.annotation.PostConstruct;
import java.sql.*;
import java.util.List;

@RestController
@RequestMapping("/project")
public class ProjectController {

    private final DbManager dbManager;
    private final DaoProject daoProject;
    private Connection connection;

    public ProjectController(DbManager dbManager, DaoProject daoProject) {
        this.dbManager = dbManager;
        this.daoProject = daoProject;
    }


    @GetMapping("/{id}")
    public ProjectD getProjectById(@PathVariable Long id){

        return daoProject.getProjectById(id);
    }

    @GetMapping("/title")
    public ProjectD getProjectByTitle(@RequestParam String title){

        return daoProject.getProjectByTitle(title);
    }

    @GetMapping("/project-by-person")
    public List<ProjectD> getProjectByPerson(@RequestParam String name){
        return daoProject.getProjectByPersonName(name);
    }

    @PostMapping("/create")
    public HttpStatus createProject(@RequestParam String title){
        boolean result = daoProject.createProject(title);
        if(result){
            return HttpStatus.CREATED;
        }else {
            return HttpStatus.BAD_GATEWAY;
        }
    }

    @PostMapping("/update")
    public HttpStatus updateProject(@RequestParam String oldTitle,
                                    @RequestParam String  newTitle){
        boolean result = daoProject.updateProject(oldTitle,newTitle);
        if(result){
            return HttpStatus.CREATED;
        }else {
            return HttpStatus.BAD_GATEWAY;
        }
    }

    @PostMapping("/add-person-in-project")
    public HttpStatus addPersonInProject(@RequestParam String namePerson,
                                    @RequestParam String titleProject){
        boolean result = daoProject.addPersonInProject(namePerson, titleProject);
        if(result){
            return HttpStatus.CREATED;
        }else {
            return HttpStatus.BAD_GATEWAY;
        }
    }

    @PostMapping("/remove-person-in-project")
    public HttpStatus removePersonInProject(@RequestParam String namePerson,
                                         @RequestParam String titleProject){
        boolean result = daoProject.removePersonInProject(namePerson, titleProject);
        if(result){
            return HttpStatus.CREATED;
        }else {
            return HttpStatus.BAD_GATEWAY;
        }
    }

    @DeleteMapping("/delete")
    public HttpStatus deleteProject(@RequestParam String titleProjectt){
        boolean result = daoProject.deleteProject(titleProjectt);
        if(result){
            return HttpStatus.CREATED;
        }else {
            return HttpStatus.BAD_GATEWAY;
        }
    }




    @GetMapping("/all")
    public List<ProjectD> getAllProject(){

        return daoProject.getAllProject();

    }


    @PostConstruct
    private void postConstruct(){
        connection = dbManager.getConnection();
    }




}
