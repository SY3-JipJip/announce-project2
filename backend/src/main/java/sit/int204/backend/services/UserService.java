package sit.int204.backend.services;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.stereotype.Service;
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

    private Argon2PasswordEncoder argon2PasswordEncoder = new Argon2PasswordEncoder(16,32,1,4096,3);


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
//                argon2PasswordEncoder.encode(userDTO.getPassword().trim()),
                userDTO.getPassword().trim(),
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
        if (user == null) {
            throw new ResourceNotFoundException("Username is " + userMatchDTO.getUsername() + " not found!!!");
        } else if (user.getPassword().matches(userMatchDTO.getPassword())) {
//        } else if (argon2PasswordEncoder.matches(userMatchDTO.getPassword(),user.getPassword())) {
            return user;
        } else {
            throw new UnauthorizedException( "Password is not matching!!!");
        }
    }
}
