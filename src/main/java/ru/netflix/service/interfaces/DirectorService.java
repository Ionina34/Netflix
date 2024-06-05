package ru.netflix.service.interfaces;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import ru.netflix.model.Director;
import ru.netflix.model.Film;

public interface DirectorService {
	List<Director> getAll();
	Page<Director> findAllDirectors(Pageable pageable);
	List<Director> findDirectorsByFilmsId(Long filmId);
	Director findDirectorById(Long id);
	Director findByName(String name);
	
	Page<Director> sort(Pageable pageable, String name,String birthday,String created_date);
	
	void updateDirector(Long directorId,Director updateDirector);
	void saveDirector(Director director);

	void updateFilmDirectors(List<Director> directorsUpdate,Film film);
	void updateDirectorFilms(List<Film> filmsUpdate,Director director);
	
	void addFilmDirectors(List<Director> directors,Film film);
	void addDirectorFilms(List<Film> films,Director director);
	
	void delete(Long directorId);
}
