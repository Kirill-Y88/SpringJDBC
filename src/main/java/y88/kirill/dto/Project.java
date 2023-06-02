package y88.kirill.dto;

import java.util.List;

public class Project {

    private Long id;
    private String title;
    private List<Person> persons;

    public Project(Long id, String title) {
        this.id = id;
        this.title = title;
    }

    public Project(Long id, String title, List<Person> persons) {
        this.id = id;
        this.title = title;
        this.persons = persons;
    }

    public Project() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public List<Person> getPersons() {
        return persons;
    }

    public void setPersons(List<Person> persons) {
        this.persons = persons;
    }
}
