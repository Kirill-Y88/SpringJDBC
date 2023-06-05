package y88.kirill.services;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import y88.kirill.dto.ProjectDto;
import y88.kirill.exceptions.ExceptionInfo;
import y88.kirill.models.Person;
import y88.kirill.models.Project;
import y88.kirill.repositories.ProjectRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;

    public ProjectDto getById(Long id) throws ExceptionInfo {
        try {
            Project project = projectRepository.findById(id);
            return toDto(project);
        } catch (java.lang.NullPointerException e) {
            throw new ExceptionInfo("There is no project with the given id",e.getMessage());
        }
    }

    public ProjectDto getByTitle(String title) throws ExceptionInfo {
        try {
            Project project = projectRepository.findByTitle(title);
            return toDto(project);
        } catch (java.lang.NullPointerException e) {
            throw new ExceptionInfo("There is no project with the given title",e.getMessage());
        }
    }

    public List<ProjectDto> getAll() {
        return projectRepository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public void create(String title) throws ExceptionInfo {
        try {
            projectRepository.create(title);
        } catch (javax.persistence.NoResultException e) {
            throw new ExceptionInfo("creation error", e.getMessage());
        }
    }

    public void updateTitle(String oldTitle, String newTitle) throws ExceptionInfo {
        try {
            projectRepository.update(oldTitle, newTitle);
        } catch (javax.persistence.NoResultException e) {
            throw new ExceptionInfo("update error", e.getMessage());
        }
    }

    public void updateAddPerson(String titleProject, String namePerson) throws ExceptionInfo {
        try {
            projectRepository.updateAddPerson(titleProject, namePerson);
        } catch (javax.persistence.NoResultException e) {
            throw new ExceptionInfo("update error", e.getMessage());
        }
    }

    public void updateRemovePerson(String titleProject, String namePerson) throws ExceptionInfo {
        try {
            projectRepository.updateRemovePerson(titleProject, namePerson);
        } catch (javax.persistence.NoResultException e) {
            throw new ExceptionInfo("update error", e.getMessage());
        }
    }

    public void deleteByTitle(String title) throws ExceptionInfo {
        try {
            projectRepository.deleteByTitle(title);
        } catch (RuntimeException e) {
            throw new ExceptionInfo("delete error", e.getMessage());
        }
    }

    public void deleteById(Long id) throws ExceptionInfo {
        try {
            projectRepository.deleteById(id);
        } catch (RuntimeException e) {
            throw new ExceptionInfo("delete error", e.getMessage());
        }
    }

    public ProjectDto toDto(Project project){
        List<String> persons = project.getPersons()
                .stream()
                .map(Person::getName)
                .collect(Collectors.toList());

        return ProjectDto.builder()
                .id(project.getId())
                .title(project.getTitle())
                .persons(persons)
                .build();
    }


}
