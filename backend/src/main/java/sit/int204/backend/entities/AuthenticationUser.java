package sit.int204.backend.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;


import java.io.Serializable;


@Getter
@Setter
@Entity
@Table(name = "users")
public class AuthenticationUser extends User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "username", nullable = false, length = 45)
    private String username;
    @Column(name = "password", nullable = false, length = 100)
    private String password;
}

