package sit.int204.backend;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;

@Configuration
public class ApplicationConfig {
@Bean
public ModelMapper modelMapper() {
return new ModelMapper();
}

@Bean
public ListMapper listMapper(){
    return ListMapper.getInstance();
}

}
