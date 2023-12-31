package sit.int204.backend.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import net.minidev.json.annotate.JsonIgnore;

import java.time.ZonedDateTime;
import java.util.ArrayList;


@Getter
@Setter
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userId", nullable = false)
    private int id;
    @Column(name = "username", nullable = false, length = 45)
    private String username;
    @JsonIgnore
    @Column(name = "password", nullable = false, length = 100)
    private String password;
    @Column(name = "name", nullable = false, length = 100)
    private String name;
    @Column(name = "email", length = 150)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false, length = 20)
    private UserRoleEnum role;


    @Column(name = "createdOn")
    private ZonedDateTime createdOn;
    @Column(name = "updatedOn",insertable = false,updatable = false )   //error เพราะ ตัวอักษรตก d
    private ZonedDateTime updatedOn;


}

