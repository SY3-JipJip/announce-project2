package sit.int204.backend.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;
import sit.int204.backend.annotations.UniqueUserUpdate;
import sit.int204.backend.entities.UserRoleEnum;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Validated
public class UserDTO {
    @NotBlank
    @Size(min = 1,max = 45)
    @UniqueUserUpdate(fieldName = "username")
    private String username;
    @NotBlank
    @Size(min = 1,max = 100)
    @UniqueUserUpdate(fieldName = "name")
    private String name;
    @NotBlank
    @Size(min = 1,max = 150)
    @Email(regexp = "^\\S+@\\S+\\.\\S+$",message = "Email should be valid")
    @UniqueUserUpdate(fieldName = "email")
    private String email;
    @NotNull
    private UserRoleEnum role;

}
