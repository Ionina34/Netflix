package ru.netflix.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ru.netflix.model.Actor;
import ru.netflix.model.Film;
import ru.netflix.repository.ActorRepository;
import ru.netflix.repository.FilmRepository;
import ru.netflix.service.interfaces.ActorService;

@Service
public class ActorServiceImpl implements ActorService {
	@Autowired
	private ActorRepository actorRepository;

	@Autowired
	private FilmRepository filmRepository;

	@Override
	public List<Actor> getAll() {
		return actorRepository.findAll();
	}

	@Override
	public Page<Actor> findAllActors(Pageable pageable) {
		return actorRepository.findAll(pageable);
	}

	@Override
	public Page<Actor> sort(Pageable pageable, String name, String birthday, String created_date) {
		return actorRepository.getActorsOrderByNameAndBirthdayAndCreatedDate(pageable, name, birthday, created_date);
	}

	@Override
	public List<Actor> findActorsByFilmsId(Long filmId) {
		return actorRepository.findActorsByFilmsId(filmId);
	}

	@Override
	public Actor findActorById(Long id) {
		return actorRepository.findById(id).orElse(null);
	}

	@Override
	public Actor findByName(String name) {
		return actorRepository.findByName(name);
	}

	@Override
	public void updateActor(Long actorId, Actor updateActor) {
		Actor actor = actorRepository.findById(actorId).orElse(null);
		if (actor != null) {
			actor.update(updateActor);

			actorRepository.save(actor);
		}
	}

	@Override
	public void saveActor(Actor actor) {
		actor.addActor(actor);
		actorRepository.save(actor);
	}

	@Override
	public void updateFilmActors(List<Actor> actorsUpdate, Film film) {
		List<Actor> actors = findActorsByFilmsId(film.getId());

		// Новые актеры фильма
		List<Actor> missingInActors = actorsUpdate.stream()
				.filter(e -> actors.stream().noneMatch(g -> g.getName().equals(e.getName()))).toList();
		// Боьше не актеры для этого фильма
		List<Actor> missingInActorsUpdate = actors.stream()
				.filter(e -> actorsUpdate.stream().noneMatch(g -> g.getName().equals(e.getName()))).toList();

		addAMovieToAActors(missingInActors, film);

		for (Actor actor : missingInActorsUpdate) {
			actor.removeFilm(film.getId());
			actorRepository.save(actor);
		}
	}

	@Override
	public void updateActorFilms(List<Film> filmsUpdate, Actor actor) {
		List<Film> films = filmRepository.findFilmsByActorsId(actor.getId());

		// Новые фильмы актера
		List<Film> missingInFilms = filmsUpdate.stream()
				.filter(e -> films.stream().noneMatch(g -> g.getName().equals(e.getName()))).toList();

		// Актер больше не в числе актерского состава этих фильмов
		List<Film> missingInFilmsUpdate = films.stream()
				.filter(e -> filmsUpdate.stream().noneMatch(g -> g.getName().equals(e.getName()))).toList();

		for (Film f : missingInFilms) {
			Film film = filmRepository.findByName(f.getName());
			if (film != null) {
				actor.addFilm(film);
				actorRepository.save(actor);
			}
		}

		for (Film f : missingInFilmsUpdate) {
			actor.removeFilm(f.getId());
			actorRepository.save(actor);
		}
	}

	@Override
	public void addFilmActors(List<Actor> actors, Film film) {
		addAMovieToAActors(actors, film);
	}

	@Override
	public void addActorFilms(List<Film> films, Actor actor) {
		for (Film f : films) {
			Film film = filmRepository.findByName(f.getName());
			actor.addFilm(film);
			actorRepository.save(actor);
		}
	}

	@Override
	public void delete(Long actorId) {
		actorRepository.deleteById(actorId);;
	}
	
	private void addAMovieToAActors(List<Actor> actors, Film film) {
		for (Actor a : actors) {
			Actor actor = actorRepository.findByName(a.getName());
			if (actor == null) {
				a.addActor(a);
				actorRepository.save(a);
				a.addFilm(film);
				actorRepository.save(a);
			} else {
				actor.addFilm(film);
				actorRepository.save(actor);
			}
		}
	}
}
