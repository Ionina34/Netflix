package ru.netflix.controller.entities.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ru.netflix.controller.entities.UserDto;
import ru.netflix.controller.entities.valid.PasswordMatches;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object>{
	@Override
	public void initialize(PasswordMatches constraintAnnotation) {
		
	}
	
	@Override
	public boolean isValid(Object obj, ConstraintValidatorContext context) {
		UserDto user=(UserDto) obj;
		return user.getPassword().equals(user.getMatchingPassword());
	}

}
