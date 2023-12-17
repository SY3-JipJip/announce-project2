package sit.int204.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import sit.int204.backend.config.JwtTokenProvider;
import sit.int204.backend.config.JwtTokenUtil;
import sit.int204.backend.dtos.*;
import sit.int204.backend.exception.ResourceNotFoundException;
import sit.int204.backend.exception.UnauthorizedException;
import sit.int204.backend.repositories.UserRepository;
import sit.int204.backend.services.JwtUserService;

@RestController
@CrossOrigin(origins = {"http://localhost:5173/","http://127.0.0.1:5173/","https://intproj22.sit.kmutt.ac.th/"})
@RequestMapping(value = "/api/token")
public class TokenController {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserService jwtService;
    @Autowired
    private UserRepository repository;

//    Get User Token
    //2.
    @GetMapping("")
    public ResponseEntity<?> refreshToken(@RequestHeader("Authorization") String oldToken) {
        if (oldToken != null && oldToken.startsWith("Bearer ")) {
            oldToken = oldToken.substring(7); // ตัด "Bearer " ออกเพื่อให้เหลือแค่ Token
            String username = jwtTokenUtil.getUsernameFromToken(oldToken);
            final UserDetails userDetails = jwtService.loadUserByUsername(username);
            final String token = jwtTokenUtil.generateToken(userDetails);

            if (token != null) {
                return ResponseEntity.ok(new JwtResponse(token, "false"));
            }
        }
        return new ResponseEntity<>("Failed to refresh token", HttpStatus.UNAUTHORIZED);
    }



    @PostMapping("")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws ResponseStatusException {
        try {
            authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
        } catch (ResponseStatusException status) {
            if (status.getStatusCode() == HttpStatus.NOT_FOUND) {
                return ResponseEntity.status(404).body("Not found user!!!");
            } else {
                return ResponseEntity.status(401).body("User unauthorized!!!");
            }
        }

        final UserDetails userDetails = jwtService.loadUserByUsername(authenticationRequest.getUsername());

        final String token = jwtTokenUtil.generateToken(userDetails);
        final String refreshToken = jwtTokenUtil.generateRefreshToken(userDetails); // เพิ่มการสร้าง refreshToken

        return ResponseEntity.ok(new JwtResponse(token, refreshToken));
    }

    private void authenticate(String username, String password) throws ResponseStatusException{
          sit.int204.backend.entities.User user = repository.findUserByUsername(username);
          if(user == null){
              throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found!!");
          }
          if (!Argon2PasswordEncoder.defaultsForSpringSecurity_v5_8().matches(password.trim(), user.getPassword())) {
              throw new UnauthorizedException("User not authorized!!");
            }
    }


}
