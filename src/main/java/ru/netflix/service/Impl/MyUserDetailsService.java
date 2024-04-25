package ru.netflix.service.Impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import ru.netflix.config.user.MyUserDetails;
import ru.netflix.model.User;
import ru.netflix.repository.UserRepository;

@Service
public class MyUserDetailsService implements UserDetailsService{
	
	@Autowired
	private UserRepository repository;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Optional<User> user=repository.findByEmail(email);
		return user.map(MyUserDetails::new)
				.orElseThrow(()->new UsernameNotFoundException(email + "not found"));
	}
	
	
}
