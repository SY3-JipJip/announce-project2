package sit.int204.backend.annotations;

import java.lang.annotation.*;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import sit.int204.backend.validators.UniqueValidate;

@Documented
@Constraint(validatedBy = UniqueValidate.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueAnnotation {
    String uniqueData() default "";
    String message() default "does not unique";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
