package sit.int204.backend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;

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
    @Column(name = "name", nullable = false, length = 100)
    private String name;
    @Column(name = "email", length = 150)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false, length = 20)
    private UserRoleEnum role;

    @Column(name = "createdOn", length = 200)
    private Instant  createdOn;
    @Column(name = "updatedOn", length = 200)   //error เพราะ ตัวอักษรตก d
    private Instant updatedOn;


    @PrePersist
    public void setCreatedOn() {
        this.createdOn = Instant .now();
        this.updatedOn = Instant .now();
    }

    @PreUpdate
    public void setUpdatedOn() {
        this.updatedOn = Instant .now();
    }

    @JsonIgnore
    @OneToMany(mappedBy = "users")
    private Set<Announcement> announces = new LinkedHashSet<>();


}

