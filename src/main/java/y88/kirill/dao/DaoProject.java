package y88.kirill.dao;

import org.springframework.stereotype.Component;
import y88.kirill.db.DBManager;
import y88.kirill.dto.Person;
import y88.kirill.dto.Project;

import javax.annotation.PostConstruct;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class DaoProject {


    private final DBManager dbManager;
    private final DaoPerson daoPerson;
    private Connection connection;


    public DaoProject(DBManager dbManager, DaoPerson daoPerson) {
        this.dbManager = dbManager;
        this.daoPerson = daoPerson;
    }


    public Project getProjectById(Long id){

        Project project = null;
        try {
            PreparedStatement ps = connection.prepareStatement("select * from projects where id = ?");
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            rs.next();
            project = new Project(rs.getLong(1), rs.getString(2));

            project.setPersons(daoPerson.getAllByProjectId(id));


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return project;
    }


    public Project getProjectByTitle(String title){

        Project project = null;
        try {
            PreparedStatement ps = connection.prepareStatement("select * from projects where title = ?");
            ps.setString(1, title);
            ResultSet rs = ps.executeQuery();
            rs.next();
            project = new Project(rs.getLong(1), rs.getString(2));

            project.setPersons(daoPerson.getAllByProjectTitle(title));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return project;
    }

    public List<Project> getProjectByPersonName(String name){

        List<Project> projects = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement("select * from persons pe \n" +
                    "join person_projects pp on pe.id = pp.person_id \n" +
                    "join projects pr on pp.project_id = pr.id \n" +
                    "where pe.name = ?");
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                projects.add(new Project(rs.getLong(6), rs.getString(7)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return projects;
    }


    public boolean createProject(String title){
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT into projects (title) values (?)");
            ps.setString(1, title);
            int updt = ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean updateProject(String oldTitle, String newTitle){
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE projects SET title = ? where title = ?");
            ps.setString(1, newTitle);
            ps.setString(2, oldTitle);
            int updt = ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }



    public boolean addPersonInProject(String namePerson, String titleProject){
        Person person = daoPerson.getPersonByName(namePerson);
        Project project = this.getProjectByTitle(titleProject);
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT into person_projects (person_id, project_id) values (?,?)");
            ps.setLong(1, person.getId());
            ps.setLong(2, project.getId());
            int updt = ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;

    }

    public boolean removePersonInProject(String namePerson, String titleProject){
        Person person = daoPerson.getPersonByName(namePerson);
        Project project = this.getProjectByTitle(titleProject);
        try {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM person_projects WHERE person_id = ? and project_id = ?");
            ps.setLong(1, person.getId());
            ps.setLong(2, project.getId());
            int updt = ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


    public boolean deleteProject(String titleProject){
        Project project = this.getProjectByTitle(titleProject);
        try {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM person_projects WHERE  project_id = ?");
            ps.setLong(1, project.getId());
            int updt = ps.executeUpdate();

            PreparedStatement ps2 = connection.prepareStatement("DELETE FROM projects WHERE  id = ?");
            ps2.setLong(1, project.getId());
            int updt2 = ps2.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }





    public List<Project> getAllProject(){
        List<Project> projects = new ArrayList<>();
        Project project = null;

        try  {
            PreparedStatement ps = connection.prepareStatement("select * from projects");

            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                projects.add(new Project(rs.getLong(1), rs.getString(2) ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return projects;
    }




    @PostConstruct
    private void postConstruct(){
        connection = dbManager.getConnection();
    }



}
