package y88.kirill.services;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import y88.kirill.dto.PersonDto;
import y88.kirill.dto.ProjectDto;
import y88.kirill.models.Person;
import y88.kirill.models.Project;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectService {







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
