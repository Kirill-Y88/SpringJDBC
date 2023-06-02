package y88.kirill.db;


import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.sql.*;

@Component
public class DBManager {
    private  String jdbcUrl;
    private  String user;
    private  String password;
    private Connection connection;


//
//
//    public DBManager(String jdbcUrl,String user, String password) {
//        this.jdbcUrl = jdbcUrl;
//        this.user = user;
//        this.password = password;
//    }

    public void init(String jdbcUrl,String user, String password){
        this.jdbcUrl = jdbcUrl;
        this.user = user;
        this.password = password;
    }

    public  void connect()  {
        try {
            connection = DriverManager.getConnection(jdbcUrl, user,password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void disconnect() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection(){
        return connection;
    }


    @PostConstruct
    private void postConstruct() throws ClassNotFoundException {
        System.out.println("--------------postConstruct---------------start");
        System.out.println("this.connect()" + this.connection);
        Class.forName("org.postgresql.Driver");
        this.init("jdbc:postgresql://localhost:5432/WTG?currentSchema=jdbc", "postgres","1917");
        this.connect();
        System.out.println("After connection");
        System.out.println("this.connect()" + this.connection);
        System.out.println("--------------postConstruct---------------end");
    }

    @PreDestroy
    public void preDestroy() throws SQLException {
        System.out.println("--------------preDestroy---------------start");
        connection.close();
        System.out.println("--------------preDestroy---------------end");
    }


}
