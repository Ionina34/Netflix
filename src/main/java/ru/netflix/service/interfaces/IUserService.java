package ru.netflix.service.interfaces;

import ru.netflix.controller.entities.UserDto;
import ru.netflix.model.User;

public interface IUserService {
	User registerNewUserAccount(UserDto userDto);
}
