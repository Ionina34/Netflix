package ru.netflix.controller.entities;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import ru.netflix.controller.entities.valid.PasswordMatches;
import ru.netflix.controller.entities.valid.ValidEmail;

@Data
@PasswordMatches
public class UserDto {
	@NotNull
	@NotEmpty
	public String name;
	
	@NotNull
	@NotEmpty
	private String password;
    private String matchingPassword;
    
    @ValidEmail
	@NotNull
	@NotEmpty
    private String email;
	
}
