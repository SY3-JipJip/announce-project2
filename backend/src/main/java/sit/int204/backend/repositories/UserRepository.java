package sit.int204.backend.repositories;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import sit.int204.backend.entities.User;

public interface UserRepository extends JpaRepository<User,Integer> {
    User findUserByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByName(String name);
    boolean existsByEmail(String email);
    boolean existsByUsernameAndIdNot(String username, int id);
    boolean existsByNameAndIdNot(String name, int id);
    boolean existsByEmailAndIdNot(String email, int id);
    boolean existsById(int id);
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO users (username, password, name, email, role) VALUES (:username, :password, :name, :email, :role)", nativeQuery = true)
    void insertUser(String username,String password, String name, String email, String role);

    @Query(value = "SELECT * FROM users ORDER BY userId DESC LIMIT 1", nativeQuery = true)
    User findInsert();

    @Modifying
    @Transactional
    @Query(value = "UPDATE users SET username = :username, password = :password, name= :name, email= :email, role= :role WHERE userId = :id", nativeQuery = true)
    void updateUser(Integer id,String username,String password, String name, String email, String role);




}

