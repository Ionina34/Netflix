package ru.netflix.service.Impl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.netflix.model.Actor;
import ru.netflix.model.Country;
import ru.netflix.model.Film;
import ru.netflix.repository.ActorRepository;
import ru.netflix.service.interfaces.ActorService;

@Service
public class ActorServiceImpl implements ActorService {
	@Autowired
	private ActorRepository actorRepository;

	@Override
	public List<Actor> getAll() {
		return actorRepository.findAll();
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
	public void updateFilmActors(List<Actor> actorsUpdate, Film film) {
		List<Actor> actors = findActorsByFilmsId(film.getId());

		// Новые актеры фильма
		List<Actor> missingInCountries = actorsUpdate.stream()
				.filter(e -> actors.stream().noneMatch(g -> g.getName().equals(e.getName()))).toList();
		// Боьше не актеры для этого фильма
		List<Actor> missingInCountriesUpdate = actors.stream()
				.filter(e -> actorsUpdate.stream().noneMatch(g -> g.getName().equals(e.getName()))).toList();

		for (Actor a : missingInCountries) {
			Actor actor = actorRepository.findByName(a.getName());
			if (actor == null) {
				a.setCreated_at(LocalDate.now());
				a.setUpdated_at(LocalDate.now());
				actorRepository.save(a);
				a.addFilm(film);
				actorRepository.save(a);
			} else {
				actor.addFilm(film);
				actorRepository.save(actor);
			}
		}

		for (Actor actor : missingInCountriesUpdate) {
			actor.removeFilm(film.getId());
			actorRepository.save(actor);
		}
	}
}
