package ru.netflix.service.interfaces;

import java.util.List;

import ru.netflix.model.Actor;
import ru.netflix.model.Film;

public interface ActorService {
	List<Actor> getAll();
	List<Actor> findActorsByFilmsId(Long filmId);
	Actor findActorById(Long id);
	
	void updateFilmActors(List<Actor> actorsUpdate,Film film);
}
