package y88.kirill.dao;


import org.springframework.stereotype.Component;
import y88.kirill.db.DBManager;
import y88.kirill.dto.Person;
import y88.kirill.dto.Position;
import y88.kirill.dto.Project;

import javax.annotation.PostConstruct;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class DaoPosition {

    private final DBManager dbManager;
    private Connection connection;


    public DaoPosition(DBManager dbManager) {
        this.dbManager = dbManager;
    }

    public Position getPositionById(Long id){
        Position position = null;
        try {
            PreparedStatement ps = connection.prepareStatement("select * from positions where id = ?");
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            rs.next();
            position = new Position(rs.getLong(1), rs.getString(2));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return position;
    }

    public Position getPositionByTitle(String title){
        Position position = null;
        try {
            PreparedStatement ps = connection.prepareStatement("select * from positions where title = ?");
            ps.setString(1, title);
            ResultSet rs = ps.executeQuery();
            rs.next();
            position = new Position(rs.getLong(1), rs.getString(2));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return position;
    }


    public List<Position> getAllPosition(){
        List<Position> positions = new ArrayList<>();

        try  {
            PreparedStatement ps = connection.prepareStatement("select * from positions");
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                positions.add(new Position(rs.getLong(1), rs.getString(2) ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return positions;
    }

    public boolean createNewPosition(String newPositionName){
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO positions (title) VALUES (?)");
            ps.setString(1,newPositionName);
            int updt = ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean updatePosition(String oldTitle, String newTitle){
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE positions SET title = ? WHERE title = ?");
            ps.setString(1,newTitle);
            ps.setString(2,oldTitle);
            int updt = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean deletePosition(String title){
        try {
            PreparedStatement ps = connection.prepareStatement("DELETE from positions  WHERE title = ?");
            ps.setString(1,title);
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
