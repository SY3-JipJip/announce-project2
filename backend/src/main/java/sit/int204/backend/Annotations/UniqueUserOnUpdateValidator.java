package sit.int204.backend.Annotations;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sit.int204.backend.repositories.UserRepository;

import java.io.IOException;

@Component
public  class UniqueUserOnUpdateValidator extends HttpServlet implements ConstraintValidator<UniqueUserUpdate, Object> {

    @Autowired
    private UserRepository userRepository;
    private String fieldName;

    @Autowired
    private HttpServletRequest httpServletRequest;
    @Override
    public void initialize(UniqueUserUpdate constraintAnnotation) {
        fieldName = constraintAnnotation.fieldName();
    }
    private String Convert(String uri){
        String[] partList = uri.split("/");
        return partList[partList.length-1];
    }
    public Integer getId() throws ServletException, IOException {
        String id = Convert(httpServletRequest.getRequestURI());
        return Integer.parseInt(id);
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        try {
//            System.out.println(getId());
            Integer id = getId();
            if (value == null) {
                return true;
            }

            Long userWithSameValue = userRepository.findUniqueUserOnUpdate(fieldName, ((String) value).trim(),id);
            return userWithSameValue == 0;
        } catch (ServletException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
