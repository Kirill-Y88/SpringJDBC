package y88.kirill.services;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import y88.kirill.dto.PersonDto;
import y88.kirill.exceptions.ExceptionInfo;
import y88.kirill.models.Person;
import y88.kirill.models.Project;
import y88.kirill.repositories.PersonRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;

    public PersonDto getById(Long id) throws ExceptionInfo {
        try {
            Person person = personRepository.findById(id);
            return toDto(person);
        } catch (java.lang.NullPointerException e) {
            throw new ExceptionInfo("There is no person with the given id",e.getMessage());
        }
    }

    public PersonDto getByName(String name) throws ExceptionInfo {
        try {
            Person person = personRepository.findByName(name);
            return toDto(person);
        } catch (javax.persistence.NoResultException e) {
            throw new ExceptionInfo("There is no person with the given name",e.getMessage());
        }
    }

    public List<PersonDto> getAll() {
        return personRepository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public void create(String name, Long idPosition) throws ExceptionInfo {
        try {
            personRepository.create(name, idPosition);
        } catch (javax.persistence.NoResultException e) {
            throw new ExceptionInfo("creation error",e.getMessage());
        }
    }

    public void updateName(String oldName, String newName) throws ExceptionInfo {
        try {
            personRepository.updateName(oldName, newName);
        } catch (javax.persistence.NoResultException e) {
            throw new ExceptionInfo("update error",e.getMessage());
        }
    }

    public void updatePosition(String name, String positionTitle) throws ExceptionInfo {
        try {
            personRepository.updatePosition(name, positionTitle);
        } catch (javax.persistence.NoResultException e) {
            throw new ExceptionInfo("update error",e.getMessage());
        }
    }

    public void deleteByName(String name) throws ExceptionInfo {
        try {
            personRepository.deleteByName(name);
        } catch (RuntimeException e) {
            throw new ExceptionInfo("delete error",e.getMessage());
        }
    }

    public void deleteById(Long id) throws ExceptionInfo {
        try {
            personRepository.deleteById(id);
        } catch (javax.persistence.NoResultException e) {
            throw new ExceptionInfo("delete error",e.getMessage());
        }
    }

    public PersonDto toDto(Person person) {
        List<String> projects = person.getProjects()
                .stream()
                .map(Project::getTitle)
                .collect(Collectors.toList());

        return PersonDto.builder()
                .id(person.getId())
                .name(person.getName())
                .position(person.getPosition().getTitle())
                .projects(projects)
                .build();
    }


}
