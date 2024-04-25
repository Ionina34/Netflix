package ru.netflix.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ru.netflix.model.User;

@Repository
public interface UserRepository extends JpaRepository<User,Long>{
	Optional<User> findByName(String username);
	
	Optional<User> findByEmail(String email);
}
