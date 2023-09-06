package sit.int204.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sit.int204.backend.dtos.UserDTO;
import sit.int204.backend.entities.User;
import sit.int204.backend.exception.ResourceNotFoundException;
import sit.int204.backend.repositories.UserRepository;

import java.time.Instant;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;


    //Get All Users
    public List<User> getAllUsers() {
        return repository.findAll();
    }

    //Get 1 User
    public User getUserById(int id){
        return repository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("User Id" + id + "DOES NOT EXIST !!!"));
    }

    //Create User
    public User createUser(UserDTO userDTO){
        repository.insertUser(userDTO.getUsername(),userDTO.getName(),userDTO.getEmail(),userDTO.getRole().toString());
        return repository.findInsert();
    }

    //Update User
    public User updateUser(int id, UserDTO userDTO) {
        repository.updateUser(id,userDTO.getUsername(),userDTO.getName(),userDTO.getEmail(),userDTO.getRole().toString());
        return getUserById(id);
    }

    // Delete User
    public User deleteUser(int id) {
        User user = repository.findById(id).orElseThrow(() -> new RuntimeException(id + " DOES NOT EXIST !!!"));
        repository.delete(user);
        return user;
    }

}
