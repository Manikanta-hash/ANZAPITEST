package dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataListDTO {
    private String id;
    private String email;
    @JsonProperty(value="first_name")
    private String firstName;
    @JsonProperty(value="last_name")
    private String lastName;
}
