package sit.int204.backend.controllers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import sit.int204.backend.Annotations.UpdateUserAnnotation;
import sit.int204.backend.dtos.CreateUserDTO;
import sit.int204.backend.dtos.OutputUserDTO;
import sit.int204.backend.dtos.UserDTO;
import sit.int204.backend.dtos.UserMatchDTO;
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


    //Get User
    @GetMapping("")
    public List<User> getUsers() {
        return service.getAllUsers();
    }


    //Get User By Id
    @GetMapping("/{id}")
    public User getUsersById(@PathVariable int id) {
        return service.getUserById(id);
    }

    //Create User
    @PostMapping("")
    public OutputUserDTO createUser(@Valid @RequestBody CreateUserDTO createUserDTO ){
         return modelMapper.map(service.createUser(createUserDTO), OutputUserDTO.class);
    }


//    Update User
    @PutMapping("/{id}")
    @UpdateUserAnnotation
    public User updateUserById(@PathVariable int id, @Valid @RequestBody UserDTO userDTO, BindingResult bindingResult) throws MethodArgumentNotValidException {
        if(bindingResult.hasErrors()){ throw new MethodArgumentNotValidException((MethodParameter) null, bindingResult);
        }
        return service.updateUser(id, userDTO);
    }


    //Delete User
    @DeleteMapping("/{id}")
    public User deleteUserById (@PathVariable int id) {
        return service.deleteUser(id);
    }


    //Matching Username & Password
    @PostMapping("/match")
    public User matchUsernameAndPsw(@RequestBody UserMatchDTO userMatchDTO){
        return service.matchPsw(userMatchDTO);
    }

}
