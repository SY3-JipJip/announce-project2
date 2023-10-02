package sit.int204.backend.controllers;

import java.util.List;

import jakarta.validation.Valid;
import org.apache.tomcat.util.net.openssl.ciphers.Encryption;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import sit.int204.backend.config.JwtTokenUtil;
import sit.int204.backend.dtos.*;
import sit.int204.backend.entities.User;
import sit.int204.backend.exception.ResourceNotFoundException;
import sit.int204.backend.exception.UnauthorizedException;
import sit.int204.backend.repositories.UserRepository;
import sit.int204.backend.services.JwtUserService;

@RestController
@CrossOrigin
@RequestMapping(value = "/api/token")
public class TokenController {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserService userDetailsService;
    @Autowired
    private UserRepository repository;


//    Get User Token
//    @GetMapping("")
//    public List<User> getUsersToken() {
//
//    }


// Create User Token
    @PostMapping("")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
        try {
            authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
        }catch (ResponseStatusException status){
            if (status.getStatusCode() == HttpStatus.NOT_FOUND){
                return ResponseEntity.status(404).body("Not found!!!");
            }else {
                return ResponseEntity.status(401).body("User unauthorized!!!");
            }
        }
        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());

        final String token = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new JwtResponse(token));

    }

    private void authenticate(String username, String password) throws Exception {
        try {
          sit.int204.backend.entities.User user = repository.findUserByUsername(username);
          if(user == null){
              throw new ResourceNotFoundException("User not found!!");
          }
          if (!Argon2PasswordEncoder.defaultsForSpringSecurity_v5_8().matches(password.trim(), user.getPassword())) {
              throw new UnauthorizedException("User not authorized!!");
            }
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }





//    @GetMapping
//    public ResponseEntity<List<User>> getAllUsers() {
//        List<User> users = userDetailsService.getAllUsersToken();
//        return new ResponseEntity<>(users, HttpStatus.OK);
//    }

    // เพิ่มผู้ใช้ใหม่ด้วยวิธี POST
//    @PostMapping
//    public ResponseEntity<User> createUser(@RequestBody User newUser) {
//        // ทำการบันทึกผู้ใช้ใหม่ลงในระบบ
//        // และคืนข้อมูลผู้ใช้ที่ถูกสร้างขึ้น
//        User createdUser = newUser;
//
//        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
//    }


}
