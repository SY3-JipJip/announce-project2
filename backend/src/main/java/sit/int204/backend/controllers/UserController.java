package sit.int204.backend.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import sit.int204.backend.annotations.UpdateUserAnnotation;
import sit.int204.backend.config.JwtTokenUtil;
import sit.int204.backend.dtos.CreateUserDTO;
import sit.int204.backend.dtos.OutputUserDTO;
import sit.int204.backend.dtos.UserDTO;
import sit.int204.backend.dtos.UserMatchDTO;
import sit.int204.backend.entities.User;
import sit.int204.backend.exception.ForbiddenException;
import sit.int204.backend.services.UserService;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@RestController
@CrossOrigin(origins = {"http://localhost:5173/","http://127.0.0.1:5173/","https://intproj22.sit.kmutt.ac.th/"})
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService service;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

//    @GetMapping("")
//    public List<OutputUserDTO> getUsers() {
//        List<User> users = service.getAllUsers();
//
//        // ใช้ ModelMapper หรือแปลงข้อมูลเอง
//        ModelMapper modelMapper = new ModelMapper();
//        List<OutputUserDTO> outputUserDTOs = users.stream()
//                .map(user -> modelMapper.map(user, OutputUserDTO.class))
//                .collect(Collectors.toList());
//
//        return outputUserDTOs;
//    }

    @GetMapping("")
    public ResponseEntity<List<OutputUserDTO>> getUsers(HttpServletRequest request) {
        String role = jwtTokenUtil.getRoleFromToken(request.getHeader("Authorization").substring(7));
        System.out.println(role);
        if(Objects.equals(role, "admin")){
            try {
                List<User> users = service.getAllUsers();

                ModelMapper modelMapper = new ModelMapper();
                List<OutputUserDTO> outputUserDTOs = users.stream()
                        .map(user -> modelMapper.map(user, OutputUserDTO.class))
                        .collect(Collectors.toList());
                System.out.println(users.get(0).getEmail());
                return ResponseEntity.ok(outputUserDTOs);
            } catch (ForbiddenException e) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
            }
        }else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }

    }


    @GetMapping("/{id}")
    public OutputUserDTO getUsersById(@PathVariable int id, @RequestHeader("Authorization") String token) {
        String role = jwtTokenUtil.getRoleFromToken(token.substring(7));
        System.out.println(role);
        if(Objects.equals(role, "admin")){
            return modelMapper.map(service.getUserById(id), OutputUserDTO.class);
        }else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
    }



    //Create User
    @PostMapping("")
    public OutputUserDTO createUser(@RequestBody CreateUserDTO createUserDTO, @RequestHeader("Authorization") String token){
        String role = jwtTokenUtil.getRoleFromToken(token.substring(7));
        System.out.println(role);
        if(Objects.equals(role, "admin")){
            System.out.println(role);
            return modelMapper.map(service.createUser(createUserDTO), OutputUserDTO.class);
        }else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping("/")
    public OutputUserDTO createUser2(@RequestBody CreateUserDTO createUserDTO, @RequestHeader("Authorization") String token){
        String role = jwtTokenUtil.getRoleFromToken(token.substring(7));
        System.out.println(role);
        if(Objects.equals(role, "admin")){
            System.out.println(role);
            return modelMapper.map(service.createUser(createUserDTO), OutputUserDTO.class);
        }else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
    }


//    Update User
    @PutMapping("/{id}")
    @UpdateUserAnnotation
    public User updateUserById(@PathVariable int id, @RequestBody UserDTO userDTO, BindingResult bindingResult, @RequestHeader("Authorization") String token) throws MethodArgumentNotValidException {
        String role = jwtTokenUtil.getRoleFromToken(token.substring(7));
        System.out.println(role);
        if(Objects.equals(role, "admin")){
            if(bindingResult.hasErrors()){ throw new MethodArgumentNotValidException((MethodParameter) null, bindingResult);
            }
            return service.updateUser(id, userDTO);
        }else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
    }


    //Delete User
    @DeleteMapping("/{id}")
    public User deleteUserById (@PathVariable int id,@RequestHeader("Authorization") String token) {
        String role = jwtTokenUtil.getRoleFromToken(token.substring(7));
        System.out.println(role);
        if(Objects.equals(role, "admin")){
            return service.deleteUser(id, token);
        }else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
    }


    //Matching Username & Password
    @PostMapping("/match")
    public User matchUsernameAndPsw(@RequestBody UserMatchDTO userMatchDTO, @RequestHeader("Authorization") String token){
        String role = jwtTokenUtil.getRoleFromToken(token.substring(7));
        System.out.println(role);
        if(Objects.equals(role, "admin")){
            return service.matchPsw(userMatchDTO);
        }else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
    }



}
