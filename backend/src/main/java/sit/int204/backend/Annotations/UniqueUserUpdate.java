package sit.int204.backend.Annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueUserOnUpdateValidator.class)
public @interface UniqueUserUpdate {
    String message() default "does not unique";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    String fieldName();
}
