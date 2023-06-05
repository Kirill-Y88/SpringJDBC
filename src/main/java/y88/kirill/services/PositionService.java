package y88.kirill.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import y88.kirill.dto.PositionDto;
import y88.kirill.exceptions.ExceptionInfo;
import y88.kirill.models.Person;
import y88.kirill.models.Position;
import y88.kirill.repositories.PositionRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PositionService {

    private final PositionRepository positionRepository;

    public PositionDto getById(Long id) throws ExceptionInfo {
        try {
            Position position = positionRepository.findById(id);
            return toDto(position);
        } catch (java.lang.NullPointerException e) {
            throw new ExceptionInfo("There is no position with the given id", e.getMessage());
        }
    }

    public PositionDto getByTitle(String title) throws ExceptionInfo {
        try {
            Position position = positionRepository.findByTitle(title);
            return toDto(position);
        } catch (java.lang.NullPointerException e) {
            throw new ExceptionInfo("There is no position with the given id", e.getMessage());
        }
    }

    public List<PositionDto> getAll() {
        return positionRepository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public void create(String title) throws ExceptionInfo {
        try {
            positionRepository.create(title);
        } catch (javax.persistence.NoResultException e) {
            throw new ExceptionInfo("creation error", e.getMessage());
        }
    }

    public void updateTitle(String oldTitle, String newTitle) throws ExceptionInfo {
        try {
            positionRepository.update(oldTitle, newTitle);
        } catch (javax.persistence.NoResultException e) {
            throw new ExceptionInfo("update error", e.getMessage());
        }
    }

    public void deleteByTitle(String title) throws ExceptionInfo {
        try {
            positionRepository.deleteByTitle(title);
        } catch (RuntimeException e) {
            throw new ExceptionInfo("delete error", e.getMessage());
        }
    }

    public void deleteById(Long id) throws ExceptionInfo {
        try {
            positionRepository.deleteById(id);
        } catch (RuntimeException e) {
            throw new ExceptionInfo("delete error", e.getMessage());
        }
    }

    public PositionDto toDto(Position position) {
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
