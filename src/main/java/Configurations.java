import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import y88.kirill.db.DBManager;

@Configuration
@EnableWebMvc
@ComponentScan("y88.kirill")
public class Configurations {


    @Bean
    public DBManager dbManager(){
        DBManager dbManager = new DBManager();
        dbManager.init("jdbc:postgresql://localhost:5432/WTG?currentSchema=jdbc", "postgres","1917");
        dbManager.connect();
        System.out.println("-----------------------connect" + dbManager.getConnection());
        return dbManager;
    }




}
