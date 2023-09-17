package sit.int204.backend.dtos;

import lombok.Getter;
import lombok.Setter;
import sit.int204.backend.entities.UserRoleEnum;

import java.time.ZonedDateTime;

@Getter
@Setter
public class OutputUserDTO {
    private int id;
    private String username;
    private String name;
    private String email;
    private UserRoleEnum role;
    private ZonedDateTime createdOn;
    private ZonedDateTime updatedOn;
}
