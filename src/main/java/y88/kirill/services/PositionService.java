package y88.kirill.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import y88.kirill.dto.PositionDto;
import y88.kirill.models.Person;
import y88.kirill.models.Position;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PositionService {






    public PositionDto toDto(Position position){
        List<String> persons = position.getPersons()
                .stream()
                .map(Person::getName)
                .collect(Collectors.toList());
        return PositionDto.builder()
                .id(position.getId())
                .title(position.getTitle())
                .persons(persons)
                .build();
    }
}
