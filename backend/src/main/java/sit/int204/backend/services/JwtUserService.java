package sit.int204.backend.services;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.server.ResponseStatusException;
import sit.int204.backend.config.JwtTokenProvider;
import sit.int204.backend.exception.ResourceNotFoundException;
import sit.int204.backend.repositories.UserRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtUserService implements UserDetailsService {
    @Autowired
    private UserRepository repository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    //called User Token
    public String refreshToken(String oldToken) {
        // 1. ดึงข้อมูลผู้ใช้จาก oldToken โดยตรง
        String username = jwtTokenProvider.getUsernameFromToken(oldToken);

        // 2. สร้าง Token ใหม่ (Refresh Token) โดยใช้ข้อมูลผู้ใช้จาก oldToken
        String newToken = jwtTokenProvider.generateToken(username);

        return newToken;
    }

    //create User Token
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        sit.int204.backend.entities.User user = repository.findUserByUsername(username);
        if (user == null) {
            throw new ResourceNotFoundException("User not found");
        } else {
            return new User(username, user.getPassword(), new ArrayList<>());
        }
    }

}

