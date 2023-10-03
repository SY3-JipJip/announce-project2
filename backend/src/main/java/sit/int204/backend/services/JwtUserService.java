package sit.int204.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import sit.int204.backend.config.JwtTokenProvider;
import sit.int204.backend.dtos.JwtResponse;
import sit.int204.backend.repositories.UserRepository;

import java.util.ArrayList;


@Service
public class JwtUserService implements UserDetailsService {
    @Autowired
    private UserRepository repository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    //called User Token
    //1.
//    public String refreshToken(String oldToken) {
//        // 1. ดึงข้อมูลผู้ใช้จาก oldToken โดยตรง
//        String username = jwtTokenProvider.getUsernameFromToken(oldToken);
//
//        // 2. สร้าง Token ใหม่ (Refresh Token) โดยใช้ข้อมูลผู้ใช้จาก oldToken
//        String newToken = jwtTokenProvider.generateToken(username);
//
//        return newToken;
//    }

    //2.

    public ResponseEntity<?> refreshToken(String oldToken) {
        if (oldToken != null && oldToken.startsWith("Bearer ")) {
            oldToken = oldToken.substring(7); // ตัด "Bearer " ออกเพื่อให้เหลือแค่ Token
            String newToken = jwtTokenProvider.generateToken(jwtTokenProvider.getUsernameFromToken(oldToken));

            if (newToken != null) {
//                return ResponseEntity<>(new JwtResponse(oldToken,newToken),HttpStatus.UNAUTHORIZED);
                return new ResponseEntity<>(newToken, HttpStatus.UNAUTHORIZED);
            }
        }

        return ResponseEntity.status(HttpStatus.OK).body("Failed to refresh token");

    }



    //create User Token
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        sit.int204.backend.entities.User user = repository.findUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException( "User not found");
        } else {
            return new User(username, user.getPassword(), new ArrayList<>());
        }
    }


}

