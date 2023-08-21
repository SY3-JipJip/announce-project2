package sit.int204.backend.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sit.int204.backend.ListMapper;
import sit.int204.backend.dtos.AddAnnouncementDTO;
import sit.int204.backend.dtos.OutputAnnouncement;
import sit.int204.backend.dtos.UserDTO;
import sit.int204.backend.entities.User;
import sit.int204.backend.services.UserService;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService service;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ListMapper listMapper;

    //Get User
    @GetMapping("")
    public List<User> getUsers() {
        return service.getAllUsers();
    }

    ;

    //Get User By Id
    @GetMapping("/{id}")
    public User getUsersById(@PathVariable int id) {
        return service.getUserById(id);
    }

    //Create User
    @PostMapping("")
    public UserDTO createUser(@RequestBody UserDTO userDTO){
        return modelMapper.map(service.createUser(userDTO), UserDTO.class);
    }

    //Update User
    @PutMapping("/{id}")
    public User updateUser(@PathVariable int id,@RequestBody UserDTO userDTO){
        return service.updateUser(id, userDTO);
    }

    //Delete User
    @DeleteMapping("/{id}")
    public User deleteUserById (@PathVariable int id) {
        return service.deleteUser(id);
    }

}
