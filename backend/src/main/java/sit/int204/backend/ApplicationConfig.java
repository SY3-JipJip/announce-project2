package sit.int204.backend;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {
@Bean
public ModelMapper modelMappers() {
return new ModelMapper();
}

@Bean
public ListMapper listMapper(){
    return ListMapper.getInstance();
}
}
