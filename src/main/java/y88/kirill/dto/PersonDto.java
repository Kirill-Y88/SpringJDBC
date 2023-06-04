package y88.kirill.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
@Builder
public class PersonDto {

    private Long id;
    private String name;
    private String position;
    private List<String> projects;

}
