package ru.netflix.service.Impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> user=repository.findByName(username);
		return user.map(MyUserDetails::new)
				.orElseThrow(()->new UsernameNotFoundException(username + "not found"));
	}

}
