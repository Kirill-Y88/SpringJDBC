package y88.kirill.dao;


import org.springframework.stereotype.Component;
import y88.kirill.db.DbManager;
import y88.kirill.dto.PersonD;

import javax.annotation.PostConstruct;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class DaoPerson {

    private final DbManager dbManager;
    private final DaoPosition daoPosition;
    private Connection connection;

    public DaoPerson(DbManager dbManager, DaoPosition daoPosition) {
        this.dbManager = dbManager;
        this.daoPosition = daoPosition;
    }

    public PersonD getPersonByName(String name){
        PersonD personD = null;
        try {
            PreparedStatement ps = connection.prepareStatement("select * from persons where name = ?");
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            rs.next();
            personD = new PersonD(rs.getLong(1),
                    rs.getString(2),
                    daoPosition.getPositionById(rs.getLong(3)));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return personD;


    }


    public List<PersonD> getAllByProjectId(Long id){
        List<PersonD> personDS = new ArrayList<>();

        try {
            PreparedStatement ps2 = connection.prepareStatement("select * from persons pe \n" +
                    "join person_projects pp on pe.id = pp.person_id where pp.project_id = ?");

            ps2.setLong(1, id);
            ResultSet rs2 = ps2.executeQuery();

            while (rs2.next()){
                personDS.add(new PersonD(rs2.getLong(1),
                        rs2.getString(2),
                        daoPosition.getPositionById(rs2.getLong(3))));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return personDS;
    }

    public List<PersonD> getAllByProjectTitle(String title){
        List<PersonD> personDS = new ArrayList<>();

        try {
            PreparedStatement ps2 = connection.prepareStatement("select * from persons pe \n" +
                    "join person_projects pp on pe.id = pp.person_id join projects pr on pp.project_id = pr.id  where pr.title = ?");

            ps2.setString(1, title);
            ResultSet rs2 = ps2.executeQuery();

            while (rs2.next()){
                personDS.add(new PersonD(rs2.getLong(1),
                        rs2.getString(2),
                        daoPosition.getPositionById(rs2.getLong(3))));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return personDS;
    }

    public List<PersonD> getAllByPositionTitle(String title){
        List<PersonD> personDS = new ArrayList<>();

        try {
            PreparedStatement ps2 = connection.prepareStatement("select * from persons pe \n" +
                    "join positions po on pe.id_position = po.id where po.title = ?");

            ps2.setString(1, title);
            ResultSet rs2 = ps2.executeQuery();

            while (rs2.next()){
                personDS.add(new PersonD(rs2.getLong(1),
                        rs2.getString(2),
                        daoPosition.getPositionById(rs2.getLong(3))));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return personDS;
    }


    public List<PersonD> getAllPerson(){
        List<PersonD> personDS = new ArrayList<>();

        try  {
            PreparedStatement ps = connection.prepareStatement("select * from persons");
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                personDS.add(new PersonD(rs.getLong(1),
                        rs.getString(2),
                        daoPosition.getPositionById(rs.getLong(3))));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return personDS;
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
