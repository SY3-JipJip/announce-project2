package sit.int204.backend.repositories;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import sit.int204.backend.dtos.UserDTO;
import sit.int204.backend.entities.User;

public interface UserRepository extends JpaRepository<User,Integer> {
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO users (username, password, name, email, role) VALUES (:username, :password,:name, :email, :role)", nativeQuery = true)
    void insertUser(String username, String password,String name, String email, String role);

    default void saveUser(String username, String password, String name, String email, String role) {
        // ตรวจสอบความสอดคล้องกับเงื่อนไขใน UserDTO
        UserDTO userDTO = new UserDTO();
        userDTO.setPassword(password);

        insertUser(username, userDTO.getPassword(), name, email, role);
    }

    @Query(value = "SELECT * FROM users ORDER BY userId DESC LIMIT 1", nativeQuery = true)
    User findInsert();

    @Modifying
    @Transactional
    @Query(value = "UPDATE users SET username = :username,name= :name, email= :email, role= :role WHERE userId = :id", nativeQuery = true)
    void updateUser(Integer id,String username, String name, String email, String role);
}

