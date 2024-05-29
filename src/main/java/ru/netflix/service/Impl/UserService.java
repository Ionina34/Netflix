package ru.netflix.service.Impl;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import ru.netflix.controller.entities.entity.request.UserDto;
import ru.netflix.model.User;
import ru.netflix.repository.UserRepository;
import ru.netflix.service.exeption.UserAlreadyExistException;
import ru.netflix.service.interfaces.IUserService;

@Service
@Transactional
public class UserService implements IUserService{
	@Autowired
	private UserRepository repository;
	
	@Autowired
	private PasswordEncoder encoder;
	
	@Override
	public User registerNewUserAccount(UserDto userDto) throws UserAlreadyExistException{
		if (EmailExists(userDto.getEmail())) {
            throw new UserAlreadyExistException("There is an account with that email address: "
              + userDto.getEmail());
        }
		
		User user=new User();
		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setPassword(encoder.encode(userDto.getPassword()));
		user.setRoles("ROLE_USER");
		user.setCreated_at(LocalDate.now());
		user.setUpdated_at(LocalDate.now());
		return repository.save(user);
	}
	
	private boolean EmailExists(String email) {
        return (repository.findByEmail(email).isPresent());
    }

	@Override
	public Optional<User> findByEmail(String email) {
		return repository.findByEmail(email);
	}

	@Override
	public User save(User user) {
		return repository.save(user);
	}

}
