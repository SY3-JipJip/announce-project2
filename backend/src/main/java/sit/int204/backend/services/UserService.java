package sit.int204.backend.services;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import sit.int204.backend.dtos.CreateUserDTO;
import sit.int204.backend.dtos.UserDTO;
import sit.int204.backend.dtos.UserMatchDTO;
import sit.int204.backend.entities.User;
import sit.int204.backend.exception.ResourceNotFoundException;
import sit.int204.backend.exception.UnauthorizedException;
import sit.int204.backend.repositories.UserRepository;


import java.util.List;

@Service
public class UserService{
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
    public User createUser(@Valid CreateUserDTO userDTO){
        repository.insertUser(userDTO.getUsername().trim(),
                Argon2PasswordEncoder.defaultsForSpringSecurity_v5_8().encode(userDTO.getPassword().trim()),
                userDTO.getName().trim(),
                userDTO.getEmail().trim(),
                userDTO.getRole().toString());
        return repository.findInsert();
    }

    //Update User
    public User updateUser(int id, UserDTO userDTO) {
        repository.updateUser(id,userDTO.getUsername().trim(),
                                 userDTO.getName().trim(),
                                 userDTO.getEmail().trim(),
                                 userDTO.getRole().toString());
        return getUserById(id);
    }

    // Delete User
    public User deleteUser(int id) {
        User user = repository.findById(id).orElseThrow(() -> new RuntimeException(id + " DOES NOT EXIST !!!"));
        repository.delete(user);
        return user;
    }

    // Password Matching
    public User matchPsw(UserMatchDTO userMatchDTO) {
        User user = repository.findUserByUsername(userMatchDTO.getUsername());
        System.out.println(user);
        if (user == null) {
            throw new ResourceNotFoundException("Username is " + userMatchDTO.getUsername() + " not found!!!");
        }
        if (Argon2PasswordEncoder.defaultsForSpringSecurity_v5_8().matches(userMatchDTO.getPassword().trim(), user.getPassword())) {
             throw new ResponseStatusException(HttpStatus.OK);
        } else {
            throw new UnauthorizedException( "Password is not matching!!!");
        }
    }
}
