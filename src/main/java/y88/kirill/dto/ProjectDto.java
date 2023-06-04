package y88.kirill.dto;


import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.List;

@Data
@NoArgsConstructor
@Builder
public class ProjectDto {

    private Long id;
    private String title;
    private List<String> persons;

}
