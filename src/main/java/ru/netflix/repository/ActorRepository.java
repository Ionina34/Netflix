package ru.netflix.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ru.netflix.model.Actor;

@Repository
public interface ActorRepository extends JpaRepository<Actor, Long>
{
	List<Actor> findActorsByFilmsId(Long filmId);
	
	Actor findByName(String name);
}
