package ru.netflix.controller.entities.valid;

import java.lang.annotation.*;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import ru.netflix.controller.entities.validator.EmailValidator;

@Target({ElementType.TYPE,ElementType.FIELD,ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EmailValidator.class)
@Documented
public @interface ValidEmail {
	String message() default "Неправильный email";
	Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
