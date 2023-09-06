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

    //password
    public void setPassword(String password) {
        // เช็คความยาว 8-14 ตัวอักษร
        if (password.length() < 8 || password.length() > 14) {
            throw new IllegalArgumentException("Password must be between 8 and 14 characters.");
        }

        // เช็คว่ามีอักขระตัวใหญ่
        if (!password.matches(".*[A-Z].*")) {
            throw new IllegalArgumentException("Password must contain at least one uppercase letter.");
        }

        // เช็คว่ามีอักขระตัวเล็ก
        if (!password.matches(".*[a-z].*")) {
            throw new IllegalArgumentException("Password must contain at least one lowercase letter.");
        }

        // เช็คว่ามีตัวเลข
        if (!password.matches(".*\\d.*")) {
            throw new IllegalArgumentException("Password must contain at least one digit.");
        }

        // เช็คว่ามีสัญลักษณ์พิเศษ
        if (!password.matches(".*[@#$%^&+=].*")) {
            throw new IllegalArgumentException("Password must contain at least one special character.");
        }

        // เช็คว่าไม่มีช่องว่าง
        if (password.contains(" ")) {
            throw new IllegalArgumentException("Password cannot contain spaces.");
        }

        this.password = password.trim();
    }

}
