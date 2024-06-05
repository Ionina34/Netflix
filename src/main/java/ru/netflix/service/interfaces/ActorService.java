package ru.netflix.service.interfaces;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import ru.netflix.model.Actor;
import ru.netflix.model.Film;

public interface ActorService {
	List<Actor> getAll();
	Page<Actor> findAllActors(Pageable pageable);
	Page<Actor> sort(Pageable pageable, String name,String birthday,String created_date);
	List<Actor> findActorsByFilmsId(Long filmId);
	Actor findActorById(Long id);
	Actor findByName(String name);
	
	void updateActor(Long actorId,Actor updateActor);
	void saveActor(Actor actor);
	
	void updateFilmActors(List<Actor> actorsUpdate,Film film);
	void updateActorFilms(List<Film> filmsUpdate,Actor actor);
	
	void addFilmActors(List<Actor> actors,Film film);
	void addActorFilms(List<Film> films,Actor actor);
	
	void delete(Long actorId);
}
