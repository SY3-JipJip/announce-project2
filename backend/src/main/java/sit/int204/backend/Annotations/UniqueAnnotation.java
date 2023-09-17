package sit.int204.backend.Annotations;

import java.lang.annotation.*;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import sit.int204.backend.Validators.UniqueValidate;

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
