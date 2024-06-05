package ru.netflix.service.interfaces;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import ru.netflix.model.Film;
import ru.netflix.model.Screenwriter;

public interface ScreenwriterService {
	List<Screenwriter> getAll();
	Page<Screenwriter> findAllScreenwriters(Pageable pageable);
	List<Screenwriter> findScreenwritersByFilmsId(Long filmdId);
	Screenwriter findScreenwriterById(Long id);
	Screenwriter findByName(String name);

	Page<Screenwriter> sort(Pageable pageable, String name,String birthday,String created_date);
	
	void updateScreenwriter(Long actorId,Screenwriter updateScreenwriter);
	void saveScreenwriter(Screenwriter screenwriter);
	
	void updateFilmScreenwriters(List<Screenwriter> screenwritersUpdate,Film film);
	void updateScreenwriterFilms(List<Film> filmsUpdate,Screenwriter screenwriter);

	void addFilmScreenwriters(List<Screenwriter> screenwriters,Film film);
	void addScreenwriterFilms(List<Film> films,Screenwriter screenwriter);
	
	void delete(Long screenwriterId);
}	
