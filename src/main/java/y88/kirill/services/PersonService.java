package y88.kirill.services;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import y88.kirill.dto.PersonDto;
import y88.kirill.dto.PositionDto;
import y88.kirill.models.Person;
import y88.kirill.models.Position;
import y88.kirill.models.Project;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PersonService  {






    public PersonDto toDto(Person person){
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
