package y88.kirill.dto;

import java.util.List;

public class PersonD {

    private Long id;
    private String name;
    private PositionD positionD;
    private List<ProjectD> projectDS;

    public PersonD() {
    }

    public PersonD(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public PersonD(Long id, String name, PositionD positionD) {
        this.id = id;
        this.name = name;
        this.positionD = positionD;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PositionD getPosition() {
        return positionD;
    }

    public void setPosition(PositionD positionD) {
        this.positionD = positionD;
    }

    public List<ProjectD> getProjects() {
        return projectDS;
    }

    public void setProjects(List<ProjectD> projectDS) {
        this.projectDS = projectDS;
    }
}
