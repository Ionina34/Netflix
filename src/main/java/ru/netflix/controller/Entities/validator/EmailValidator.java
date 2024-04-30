package ru.netflix.controller.entities.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.regex.* ;
import ru.netflix.controller.entities.valid.ValidEmail;

public class EmailValidator  implements ConstraintValidator<ValidEmail, String>{
	private Pattern pattern;
	private Matcher matcher;
	
	private static final String EMAIL_PATTERN="^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@" 
	        + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";;
	
	@Override
	public void initialize(ValidEmail constraintAnnotation) {
		
	}
	
	@Override
	public boolean isValid(String email, ConstraintValidatorContext context) {
		return (validateEmail(email));
	}
	
	private boolean validateEmail(String email) {
		pattern= Pattern.compile(EMAIL_PATTERN);
		matcher=pattern.matcher(email);
		return matcher.matches();
	}

}
