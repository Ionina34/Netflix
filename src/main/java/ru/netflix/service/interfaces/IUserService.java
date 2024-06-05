package ru.netflix.service.interfaces;

import java.util.List;
import java.util.Optional;

import ru.netflix.controller.entities.entity.request.UserDto;
import ru.netflix.model.Film;
import ru.netflix.model.User;

public interface IUserService {
	User registerNewUserAccount(UserDto userDto);
	
	Optional<User> findByEmail(String email);
	
	User save(User user);
	
	List<Film> getRatedFilmsByUserId(Long userId);
}
