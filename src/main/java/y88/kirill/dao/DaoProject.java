package y88.kirill.dao;

import org.springframework.stereotype.Component;
import y88.kirill.db.DbManager;
import y88.kirill.dto.PersonD;
import y88.kirill.dto.ProjectD;

import javax.annotation.PostConstruct;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class DaoProject {


    private final DbManager dbManager;
    private final DaoPerson daoPerson;
    private Connection connection;


    public DaoProject(DbManager dbManager, DaoPerson daoPerson) {
        this.dbManager = dbManager;
        this.daoPerson = daoPerson;
    }


    public ProjectD getProjectById(Long id){

        ProjectD projectD = null;
        try {
            PreparedStatement ps = connection.prepareStatement("select * from projects where id = ?");
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            rs.next();
            projectD = new ProjectD(rs.getLong(1), rs.getString(2));

            projectD.setPersons(daoPerson.getAllByProjectId(id));


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return projectD;
    }


    public ProjectD getProjectByTitle(String title){

        ProjectD projectD = null;
        try {
            PreparedStatement ps = connection.prepareStatement("select * from projects where title = ?");
            ps.setString(1, title);
            ResultSet rs = ps.executeQuery();
            rs.next();
            projectD = new ProjectD(rs.getLong(1), rs.getString(2));

            projectD.setPersons(daoPerson.getAllByProjectTitle(title));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return projectD;
    }

    public List<ProjectD> getProjectByPersonName(String name){

        List<ProjectD> projectDS = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement("select * from persons pe \n" +
                    "join person_projects pp on pe.id = pp.person_id \n" +
                    "join projects pr on pp.project_id = pr.id \n" +
                    "where pe.name = ?");
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                projectDS.add(new ProjectD(rs.getLong(6), rs.getString(7)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return projectDS;
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
        PersonD personD = daoPerson.getPersonByName(namePerson);
        ProjectD projectD = this.getProjectByTitle(titleProject);
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT into person_projects (person_id, project_id) values (?,?)");
            ps.setLong(1, personD.getId());
            ps.setLong(2, projectD.getId());
            int updt = ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;

    }

    public boolean removePersonInProject(String namePerson, String titleProject){
        PersonD personD = daoPerson.getPersonByName(namePerson);
        ProjectD projectD = this.getProjectByTitle(titleProject);
        try {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM person_projects WHERE person_id = ? and project_id = ?");
            ps.setLong(1, personD.getId());
            ps.setLong(2, projectD.getId());
            int updt = ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


    public boolean deleteProject(String titleProject){
        ProjectD projectD = this.getProjectByTitle(titleProject);
        try {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM person_projects WHERE  project_id = ?");
            ps.setLong(1, projectD.getId());
            int updt = ps.executeUpdate();

            PreparedStatement ps2 = connection.prepareStatement("DELETE FROM projects WHERE  id = ?");
            ps2.setLong(1, projectD.getId());
            int updt2 = ps2.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }





    public List<ProjectD> getAllProject(){
        List<ProjectD> projectDS = new ArrayList<>();
        ProjectD projectD = null;

        try  {
            PreparedStatement ps = connection.prepareStatement("select * from projects");

            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                projectDS.add(new ProjectD(rs.getLong(1), rs.getString(2) ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return projectDS;
    }




    @PostConstruct
    private void postConstruct(){
        connection = dbManager.getConnection();
    }



}
