package sit.int204.backend.dtos;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sit.int204.backend.Annotations.UniqueAnnotation;
import sit.int204.backend.entities.UserRoleEnum;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserDTO {
    @NotBlank
    @UniqueAnnotation(uniqueData = "username")
    @Size(min = 1,max = 45)
    private String username;
    @NotBlank
    @Size(min = 8,max = 14)
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%^#&*()_+{}\\[\\]:;<>,.?/~\\\\|-])[\\S]{8,14}$", message = "must be 8-14 characters long, at least 1 of uppercase, lowercase, number and special characters")
    private String password;
    @NotBlank
    @UniqueAnnotation(uniqueData = "name")
    @Size(min = 1,max = 100)
    private String name;
    @NotBlank
    @UniqueAnnotation(uniqueData = "email")
    @Size(min = 1,max = 150)
    @Email(regexp = "^\\S+@\\S+\\.\\S+$",message = "Email should be valid")
    private String email;
    @NotNull
    private UserRoleEnum role;
}
