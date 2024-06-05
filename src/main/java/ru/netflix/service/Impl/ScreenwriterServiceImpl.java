package ru.netflix.service.Impl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ru.netflix.model.Actor;
import ru.netflix.model.Film;
import ru.netflix.model.Screenwriter;
import ru.netflix.repository.FilmRepository;
import ru.netflix.repository.ScreenwriterRepository;
import ru.netflix.service.interfaces.ScreenwriterService;

@Service
public class ScreenwriterServiceImpl implements ScreenwriterService {
	@Autowired
	private ScreenwriterRepository screenwriterRepository;
	
	@Autowired
	private FilmRepository filmRepository;
	
	@Override
	public List<Screenwriter> getAll() {
		return screenwriterRepository.findAll();
	}
	
	@Override
	public Page<Screenwriter> findAllScreenwriters(Pageable pageable) {
		return screenwriterRepository.findAll(pageable);
	}
	
	@Override
	public Page<Screenwriter> sort(Pageable pageable, String name, String birthday, String created_date) {
		return screenwriterRepository.getScreenwriterOrderByNameAndBirthdayAndCreatedDate(pageable, name, birthday, created_date);
	}
	
	@Override
	public List<Screenwriter> findScreenwritersByFilmsId(Long filmdId) {
		return screenwriterRepository.findScreenwritersByFilmsId(filmdId);
	}
	
	@Override
	public Screenwriter findScreenwriterById(Long id) {
		return screenwriterRepository.findById(id).orElse(null);
	}
	
	@Override
	public Screenwriter findByName(String name) {
		return screenwriterRepository.findByName(name);
	}
	
	@Override
	public void updateScreenwriter(Long actorId, Screenwriter updateScreenwriter) {
		Screenwriter screenwriter = screenwriterRepository.findById(actorId).orElse(null);
		if (screenwriter != null) {
			screenwriter.update(updateScreenwriter);

			screenwriterRepository.save(screenwriter);
		}
	}

	@Override
	public void saveScreenwriter(Screenwriter screenwriter) {
		screenwriter.addScreenwriter(screenwriter);
		screenwriterRepository.save(screenwriter);
	}
	
	@Override
	public void updateFilmScreenwriters(List<Screenwriter> screenwritersUpdate, Film film) {
		List<Screenwriter> screenwriters = findScreenwritersByFilmsId(film.getId());

		// Новые актеры фильма
		List<Screenwriter> missingInScreenwriters = screenwritersUpdate.stream()
				.filter(e -> screenwriters.stream().noneMatch(g -> g.getName().equals(e.getName()))).toList();
		// Боьше не актеры для этого фильма
		List<Screenwriter> missingInScreenwritersUpdate = screenwriters.stream()
				.filter(e -> screenwritersUpdate.stream().noneMatch(g -> g.getName().equals(e.getName()))).toList();

		addAMovieToAScreenwriters(missingInScreenwriters, film);

		for (Screenwriter screenwriter : missingInScreenwritersUpdate) {
			screenwriter.removeFilm(film.getId());
			screenwriterRepository.save(screenwriter);
		}
	}
	
	@Override
	public void updateScreenwriterFilms(List<Film> filmsUpdate, Screenwriter screenwriter) {
		List<Film> films = filmRepository.findFilmsByScreenwritersId(screenwriter.getId());

		// Новые фильмы актера
		List<Film> missingInFilms = filmsUpdate.stream()
				.filter(e -> films.stream().noneMatch(g -> g.getName().equals(e.getName()))).toList();

		// Актер больше не в числе актерского состава этих фильмов
		List<Film> missingInFilmsUpdate = films.stream()
				.filter(e -> filmsUpdate.stream().noneMatch(g -> g.getName().equals(e.getName()))).toList();

		for (Film f : missingInFilms) {
			Film film = filmRepository.findByName(f.getName());
			if (film != null) {
				screenwriter.addFilm(film);
				screenwriterRepository.save(screenwriter);
			}
		}

		for (Film f : missingInFilmsUpdate) {
			screenwriter.removeFilm(f.getId());
			screenwriterRepository.save(screenwriter);
		}
	}

	@Override
	public void addFilmScreenwriters(List<Screenwriter> screenwriters, Film film) {
		addAMovieToAScreenwriters(screenwriters, film);
	}
	
	@Override
	public void addScreenwriterFilms(List<Film> films, Screenwriter screenwriter) {
		for (Film f : films) {
			Film film = filmRepository.findByName(f.getName());
			screenwriter.addFilm(film);
			screenwriterRepository.save(screenwriter);
		}
	}

	@Override
	public void delete(Long screenwriterId) {
		screenwriterRepository.deleteById(screenwriterId);;
	}
	
	private void addAMovieToAScreenwriters(List<Screenwriter> screenwriters,Film film) {
		for (Screenwriter c : screenwriters) {
			Screenwriter screenwriter = screenwriterRepository.findByName(c.getName());
			if (screenwriter == null) {
				c.addScreenwriter(c);
				screenwriterRepository.save(c);
				c.addFilm(film);
				screenwriterRepository.save(c);
			} else {
				screenwriter.addFilm(film);
				screenwriterRepository.save(screenwriter);
			}
		}
	}
}
