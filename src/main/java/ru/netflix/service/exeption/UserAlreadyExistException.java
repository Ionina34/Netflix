package ru.netflix.service.exeption;

public class UserAlreadyExistException extends RuntimeException{
	public UserAlreadyExistException(final String message) {
		super(message);
	}
}