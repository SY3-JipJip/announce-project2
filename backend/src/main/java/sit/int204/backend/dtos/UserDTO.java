package sit.int204.backend.dtos;

import lombok.Getter;
import lombok.Setter;
import sit.int204.backend.entities.UserRoleEnum;


@Getter
@Setter
public class UserDTO {
    private String username;
    private String password;
    private String name;
    private String email;
    private UserRoleEnum role;


}
