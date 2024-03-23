
package ru.netflix.service.Impl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import ru.netflix.model.Actor;
import ru.netflix.model.Film;
import ru.netflix.repository.ActorRepository;
import ru.netflix.repository.FilmRepository;
import ru.netflix.repository.GenreRepository;
import ru.netflix.service.FilmService;

@Service
@RequiredArgsConstructor
public class FilmServiceImpl implements FilmService{

	private final FilmRepository repository;
	
	@Override
	public List<Film> findAllFilms() {
		return repository.findAll();
	}
	
	@Override
	public List<Film> findRandomFilms(){
		return repository.getTenRandomValues();
	}
	
	@Override
	public Film getFilmById(Long id) {
		return repository.findById(id).orElse(null);
	}
	
	@Override
	public List<Film> getFilmsByGenreId(Long genreId){
		return repository.findFilmByGenresId(genreId);
	}

	@Override
	public void saveFilm(Film film) {
		film.setImage("images/"+film.getName()+".jpg");
		film.setCreate_at(LocalDate.now());
		film.setUpdate_at(LocalDate.now());
		repository.save(film);
	}

	@Override
	public void updateFilm(Long id, Film updateFilm) {
		Film film=repository.findById(id).orElse(null);
		if(film!=null) {
			film.setName(updateFilm.getName());
			film.setDes(updateFilm.getDes());
			film.setRelease_date(updateFilm.getRelease_date());
			film.setLength(updateFilm.getLength());
			if(updateFilm.getImage()!="") {
				//updateFilm.saveImage(updateFilm.getImage(),updateFilm.getName());
				//film.setImage("images/"+film.getName()+".jpg");
			}
			
			film.setUpdate_at(null);
			repository.save(film);
		}
	}

	@Override
	public void deleteFilm(Long id) {
		repository.deleteById(id);
	}
	

}
