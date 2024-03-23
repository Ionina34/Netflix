package ru.netflix.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.netflix.model.Actor;

public interface ActorRepository extends JpaRepository<Actor, Long>
{
	List<Actor> findActorsByFilmsId(Long filmId);
}
