package ru.netflix.service.interfaces;

import java.util.List;

import ru.netflix.model.Actor;

public interface ActorService {
	List<Actor> findActorsByFilmsId(Long filmId);
	Actor findActorById(Long id);
}