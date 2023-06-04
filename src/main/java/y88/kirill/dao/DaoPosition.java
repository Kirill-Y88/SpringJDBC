package y88.kirill.dao;


import org.springframework.stereotype.Component;
import y88.kirill.db.DbManager;
import y88.kirill.dto.PositionD;

import javax.annotation.PostConstruct;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class DaoPosition {

    private final DbManager dbManager;
    private Connection connection;


    public DaoPosition(DbManager dbManager) {
        this.dbManager = dbManager;
    }

    public PositionD getPositionById(Long id){
        PositionD positionD = null;
        try {
            PreparedStatement ps = connection.prepareStatement("select * from positions where id = ?");
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            rs.next();
            positionD = new PositionD(rs.getLong(1), rs.getString(2));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return positionD;
    }

    public PositionD getPositionByTitle(String title){
        PositionD positionD = null;
        try {
            PreparedStatement ps = connection.prepareStatement("select * from positions where title = ?");
            ps.setString(1, title);
            ResultSet rs = ps.executeQuery();
            rs.next();
            positionD = new PositionD(rs.getLong(1), rs.getString(2));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return positionD;
    }


    public List<PositionD> getAllPosition(){
        List<PositionD> positionDS = new ArrayList<>();

        try  {
            PreparedStatement ps = connection.prepareStatement("select * from positions");
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                positionDS.add(new PositionD(rs.getLong(1), rs.getString(2) ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return positionDS;
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
