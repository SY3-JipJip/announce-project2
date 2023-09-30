package sit.int204.backend.validators;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.constraintvalidation.SupportedValidationTarget;
import jakarta.validation.constraintvalidation.ValidationTarget;
import org.springframework.beans.factory.annotation.Autowired;
import sit.int204.backend.annotations.UpdateUserAnnotation;
import sit.int204.backend.dtos.UserDTO;
import sit.int204.backend.repositories.UserRepository;

@SupportedValidationTarget(ValidationTarget.PARAMETERS)
public class UpdateUserValidate implements ConstraintValidator<UpdateUserAnnotation, Object[]> {
    @Override
    public void initialize(UpdateUserAnnotation constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Autowired
    private UserRepository userRepository;
    @Override
    public boolean isValid(Object objects[], ConstraintValidatorContext context) {
        Integer id = null;
        UserDTO user = null;
        if(objects == null) return false;
        if (objects[0] instanceof Integer)id = (Integer) objects[0];
        if(!userRepository.existsById(id))return true;
        if (objects[0] instanceof UserDTO) user = (UserDTO) objects[0];
        if (objects[1] instanceof UserDTO) user = (UserDTO) objects[1];
        if (userRepository.existsByUsernameAndIdNot(user.getUsername(),id)){
            addValidationError(context ,"Username is already in use");
        }
        if (userRepository.existsByNameAndIdNot(user.getName(),id)){
            addValidationError(context ,"Name is already in use");
        }
        if (userRepository.existsByEmailAndIdNot(user.getEmail(),id)){
            addValidationError(context ,"Email is already in use");
        }
        //logic
        return (!userRepository.existsByUsernameAndIdNot(user.getUsername(),id) && !userRepository.existsByNameAndIdNot(user.getName(),id) && !userRepository.existsByEmailAndIdNot(user.getEmail(),id));
    }
    private void addValidationError(ConstraintValidatorContext context,String err){
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(err).addConstraintViolation();
    }
}
