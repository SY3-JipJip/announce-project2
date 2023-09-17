package sit.int204.backend.Annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import sit.int204.backend.Validators.UpdateUserValidate;

import java.lang.annotation.*;

@Constraint(validatedBy = UpdateUserValidate.class)
@Target({ElementType.METHOD, ElementType.CONSTRUCTOR })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface UpdateUserAnnotation {
    String field() default "";
    String message() default "User is not valid";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
