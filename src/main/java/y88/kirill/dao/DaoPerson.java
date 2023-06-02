package y88.kirill.dao;


import org.springframework.stereotype.Component;
import y88.kirill.db.DBManager;
import y88.kirill.dto.Person;
import y88.kirill.dto.Position;

import javax.annotation.PostConstruct;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class DaoPerson {

    private final DBManager dbManager;
    private final DaoPosition daoPosition;
    private Connection connection;

    public DaoPerson(DBManager dbManager, DaoPosition daoPosition) {
        this.dbManager = dbManager;
        this.daoPosition = daoPosition;
    }

    public Person getPersonByName(String name){
        Person person = null;
        try {
            PreparedStatement ps = connection.prepareStatement("select * from persons where name = ?");
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            rs.next();
            person = new Person(rs.getLong(1),
                    rs.getString(2),
                    daoPosition.getPositionById(rs.getLong(3)));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return person;


    }


    public List<Person> getAllByProjectId(Long id){
        List<Person> persons = new ArrayList<>();

        try {
            PreparedStatement ps2 = connection.prepareStatement("select * from persons pe \n" +
                    "join person_projects pp on pe.id = pp.person_id where pp.project_id = ?");

            ps2.setLong(1, id);
            ResultSet rs2 = ps2.executeQuery();

            while (rs2.next()){
                persons.add(new Person(rs2.getLong(1),
                        rs2.getString(2),
                        daoPosition.getPositionById(rs2.getLong(3))));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return persons;
    }

    public List<Person> getAllByProjectTitle(String title){
        List<Person> persons = new ArrayList<>();

        try {
            PreparedStatement ps2 = connection.prepareStatement("select * from persons pe \n" +
                    "join person_projects pp on pe.id = pp.person_id join projects pr on pp.project_id = pr.id  where pr.title = ?");

            ps2.setString(1, title);
            ResultSet rs2 = ps2.executeQuery();

            while (rs2.next()){
                persons.add(new Person(rs2.getLong(1),
                        rs2.getString(2),
                        daoPosition.getPositionById(rs2.getLong(3))));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return persons;
    }

    public List<Person> getAllByPositionTitle(String title){
        List<Person> persons = new ArrayList<>();

        try {
            PreparedStatement ps2 = connection.prepareStatement("select * from persons pe \n" +
                    "join positions po on pe.id_position = po.id where po.title = ?");

            ps2.setString(1, title);
            ResultSet rs2 = ps2.executeQuery();

            while (rs2.next()){
                persons.add(new Person(rs2.getLong(1),
                        rs2.getString(2),
                        daoPosition.getPositionById(rs2.getLong(3))));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return persons;
    }


    public List<Person> getAllPerson(){
        List<Person> persons = new ArrayList<>();

        try  {
            PreparedStatement ps = connection.prepareStatement("select * from persons");
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                persons.add(new Person(rs.getLong(1),
                        rs.getString(2),
                        daoPosition.getPositionById(rs.getLong(3))));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return persons;
    }


    public boolean createNewPerson(String newPersonName, Long idPosition){
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO persons (name, id_position) VALUES (?, ?)");
            ps.setString(1,newPersonName);
            ps.setLong(2,idPosition);
            int updt = ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean updatePersonName(String oldName, String newName){
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE persons SET name = ? WHERE name = ?");
            ps.setString(1,newName);
            ps.setString(2,oldName);
            int updt = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean updatePersonPosition(String name, String position){
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE persons SET id_position = ? WHERE name = ?");
            ps.setLong(1,daoPosition.getPositionByTitle(position).getId());
            ps.setString(2,name);
            int updt = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean deletePerson(String name){
        try {
            PreparedStatement ps = connection.prepareStatement("DELETE from persons  WHERE title = ?");
            ps.setString(1,name);
            int updt = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }





    @PostConstruct
    private void postConstruct(){
        connection = dbManager.getConnection();
    }


}
