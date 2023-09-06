package sit.int204.backend.dtos;

import lombok.Getter;
import lombok.Setter;
import sit.int204.backend.entities.UserRoleEnum;

import java.time.Instant;

@Getter
@Setter
public class UserDTO {
    private String username;
    private String name;
    private String email;
    private UserRoleEnum role;
    private Instant updatedOn;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email != null ? email.trim() : "";
    }

    // เพิ่มเติม
    public UserRoleEnum getRole() {
        return role;
    }

    public void setRole(UserRoleEnum role) {
        this.role = role;
    }

    public Instant getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(Instant updatedOn) {
        this.updatedOn = updatedOn;
    }
}
