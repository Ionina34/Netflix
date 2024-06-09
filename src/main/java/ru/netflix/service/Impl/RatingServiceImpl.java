package ru.netflix.service.Impl;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.netflix.model.Film;
import ru.netflix.model.Rating;
import ru.netflix.repository.FilmRepository;
import ru.netflix.repository.RatingRepository;
import ru.netflix.repository.UserRepository;
import ru.netflix.service.interfaces.RatingService;

@Service
public class RatingServiceImpl implements RatingService {

	@Autowired
	private RatingRepository ratingRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private FilmRepository filmRepository;
	
	@Override
	public Rating save(Film film, int val,Principal principal) {
		Rating rating = new Rating();
		rating.setUser(userRepository.findByEmail(principal.getName()).get());
		rating.setFilm(film);
		rating.setValue(val);
		
		return ratingRepository.save(rating);
	}
	
	@Override
	public Rating save(Long filmId, int val,Principal principal) {
		Film film=filmRepository.findById(filmId).get();
		
		Rating rating = new Rating();
		rating.setUser(userRepository.findByEmail(principal.getName()).get());
		rating.setFilm(film);
		rating.setValue(val);
		
		return ratingRepository.save(rating);
	}

	@Override
	public List<Rating> getRatingByUserIdAndFilmId(Long userId, Long filmId) {
		return ratingRepository.getRatingByUserIdAndFilmId(userId, filmId);
	}
}
