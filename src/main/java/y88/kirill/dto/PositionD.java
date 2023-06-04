package y88.kirill.dto;

import java.util.List;

public class PositionD {

    private Long id;
    private String title;
    private List<PersonD> personDS;

    public PositionD() {
    }

    public PositionD(Long id, String title) {
        this.id = id;
        this.title = title;
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

    public List<PersonD> getPersons() {
        return personDS;
    }

    public void setPersons(List<PersonD> personDS) {
        this.personDS = personDS;
    }
}
