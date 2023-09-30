package sit.int204.backend.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import sit.int204.backend.entities.AuthenticationUser;
import sit.int204.backend.entities.User;
import sit.int204.backend.exception.ResourceNotFoundException;
import sit.int204.backend.repositories.UserRepository;

import java.util.List;

@Service
public class JwtUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository repository;

    //Get All Users
    public List<User> getAllUsersToken() {
        return repository.findAll();
    }

    //create User Token
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        AuthenticationUser user = repository.findById(username).orElseThrow(
//                () -> new ResourceNotFoundException("User not found with username: " + username));
//        return user;
//    }



    public UserDetails loadUserByUsername(int id) throws UsernameNotFoundException {
        User user = repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("User not found with username: " + id));
        return (UserDetails) user;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }


    //แบบ3
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        User user = repository.findUserByUsername(username);
//        return (UserDetails) user;
//    }


}
