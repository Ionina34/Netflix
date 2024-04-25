package ru.netflix.controller.entities.valid;

import java.lang.annotation.*;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import ru.netflix.controller.entities.validator.PasswordMatchesValidator;

@Target({ElementType.TYPE,ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordMatchesValidator.class)
@Documented
public @interface PasswordMatches {
	String message() default "Пароли не совпадают";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
}
