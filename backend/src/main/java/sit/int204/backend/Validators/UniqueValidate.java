package sit.int204.backend.Validators;

import org.springframework.beans.factory.annotation.Autowired;
import sit.int204.backend.Annotations.UniqueAnnotation;
import sit.int204.backend.repositories.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Objects;

public class UniqueValidate implements ConstraintValidator<UniqueAnnotation,String>{
    private String data;
    @Autowired
    private UserRepository userRepository;
    @Override
    public void initialize(UniqueAnnotation constraintAnnotation) {
        data = constraintAnnotation.uniqueData();
    }

    @Override
    public boolean isValid(String field, ConstraintValidatorContext constraintValidatorContext) {
        if(Objects.equals(data, "username")){
            return !userRepository.existsByUsername(field);
        }else if(Objects.equals(data, "name")){
            return !userRepository.existsByName(field);
        }else if (Objects.equals(data, "email")){
            return !userRepository.existsByEmail(field);
        }else return false;
    }
}
