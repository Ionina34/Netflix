package ru.netflix.controller.entities.entity.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import ru.netflix.controller.entities.valid.PasswordMatches;
import ru.netflix.controller.entities.valid.ValidEmail;

@Data
@PasswordMatches
/** Класс для представления данных, необходимых для регистации пользователя */
public class UserDto {
	@NotNull
	@NotEmpty
	//Имя пользователя
	public String name;
	
	@NotNull
	@NotEmpty
	@Size(min = 6, message = "Пароль должен содержать не меньше 6 символов")
	//Пароль
	private String password;
	//Повтор пароля
    private String matchingPassword;
    
    @ValidEmail
	@NotNull
	@NotEmpty
	//Почта пользователя
    private String email;
	
}
