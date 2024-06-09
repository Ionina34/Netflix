package ru.netflix.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ru.netflix.model.Director;
import ru.netflix.model.Film;
import ru.netflix.model.Screenwriter;
import ru.netflix.repository.DirectorRepository;
import ru.netflix.repository.FilmRepository;
import ru.netflix.repository.ScreenwriterRepository;
import ru.netflix.service.interfaces.DirectorService;

@Service
public class DirectorServiceImpl implements DirectorService{
	@Autowired
	private DirectorRepository directorRepository;
	
	@Autowired
	private FilmRepository filmRepository;
	
	@Autowired 
	private ScreenwriterRepository screenwriterRepository;
	
	@Override
	public List<Director> getAll() {
		return directorRepository.findAll();
	}
	
	@Override
	public Page<Director> findAllDirectors(Pageable pageable) {
		return directorRepository.findAll(pageable);
	}
	
	@Override
	public Page<Director> sort(Pageable pageable, String name, String birthday, String created_date) {
		return directorRepository.getDirectorsOrderByNameAndBirthdayAndCreatedDate(pageable, name, birthday, created_date);
	}
	
	@Override 
	public List<Director> findDirectorsByFilmsId(Long filmId){
		return directorRepository.findDirectorsByFilmsId(filmId);
	}
	
	@Override
	public Director findDirectorById(Long id) {
		return directorRepository.findById(id).orElse(null);
	}
	
	@Override
	public Director findByName(String name) {
		return directorRepository.findByName(name);
	}
	
	@Override
	public void updateDirector(Long actorId, Director updateDirector) {
		Director director = directorRepository.findById(actorId).orElse(null);
		if (director != null) {
			director.update(updateDirector);

			directorRepository.save(director);
		}
		
		Screenwriter screenwriter=screenwriterRepository.findByName(director.getName());
		if(screenwriter!=null) {
			screenwriter.update(updateDirector);
			
			screenwriterRepository.save(screenwriter);
		}
	}

	@Override
	public void saveDirector(Director director) {
		director.addDirector(director);
		directorRepository.save(director);
	}

	
	@Override
	public void updateFilmDirectors(List<Director> directorsUpdate, Film film) {
		List<Director> directors = findDirectorsByFilmsId(film.getId());

		// Новые актеры фильма
		List<Director> missingInDirectors = directorsUpdate.stream()
				.filter(e -> directors.stream().noneMatch(g -> g.getName().equals(e.getName()))).toList();
		// Боьше не актеры для этого фильма
		List<Director> missingInDirectorsUpdate = directors.stream()
				.filter(e -> directorsUpdate.stream().noneMatch(g -> g.getName().equals(e.getName()))).toList();

		addAMovieToADirectors(missingInDirectors, film);

		for (Director director : missingInDirectorsUpdate) {
			director.removeFilm(film.getId());
			directorRepository.save(director);
		}
	}

	@Override
	public void updateDirectorFilms(List<Film> filmsUpdate, Director director) {
		List<Film> films = filmRepository.findFilmsByDirectorsId(director.getId());

		// Новые фильмы актера
		List<Film> missingInFilms = filmsUpdate.stream()
				.filter(e -> films.stream().noneMatch(g -> g.getName().equals(e.getName()))).toList();

		// Актер больше не в числе актерского состава этих фильмов
		List<Film> missingInFilmsUpdate = films.stream()
				.filter(e -> filmsUpdate.stream().noneMatch(g -> g.getName().equals(e.getName()))).toList();

		for (Film f : missingInFilms) {
			Film film = filmRepository.findByName(f.getName());
			if (film != null) {
				director.addFilm(film);
				directorRepository.save(director);
			}
		}

		for (Film f : missingInFilmsUpdate) {
			director.removeFilm(f.getId());
			directorRepository.save(director);
		}
	}
	
	@Override
	public void addFilmDirectors(List<Director> directors, Film film) {
		addAMovieToADirectors(directors, film);
	}
	
	@Override
	public void addDirectorFilms(List<Film> films, Director director) {
		for (Film f : films) {
			Film film = filmRepository.findByName(f.getName());
			director.addFilm(film);
			directorRepository.save(director);
		}
	}

	@Override
	public void delete(Long directorId) {
		directorRepository.deleteById(directorId);;
	}
	
	private void addAMovieToADirectors(List<Director> directors,Film film) {
		for (Director d : directors) {
			Director director = directorRepository.findByName(d.getName());
			if (director == null) {
				d.addDirector(d);
				directorRepository.save(d);
				d.addFilm(film);
				directorRepository.save(d);
			} else {
				director.addFilm(film);
				directorRepository.save(director);
			}
		}
	}
}