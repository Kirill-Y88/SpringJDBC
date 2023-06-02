import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;



public class Launcher extends AbstractAnnotationConfigDispatcherServletInitializer {


    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[0];
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{Configurations.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }
}
