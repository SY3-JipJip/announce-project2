package sit.int204.backend.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.server.ResponseStatusException;
import sit.int204.backend.repositories.UserRepository;

import java.util.ArrayList;

@Service
public class JwtUserService implements UserDetailsService {
    @Autowired
    private UserRepository repository;


    //create User Token
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        sit.int204.backend.entities.User user = repository.findUserByUsername(username);
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        } else {
            return new User(username, user.getPassword(), new ArrayList<>());
        }
        }
    }

