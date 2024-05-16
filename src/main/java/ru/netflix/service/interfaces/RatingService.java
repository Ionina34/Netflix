package ru.netflix.service.interfaces;

import java.util.List;

import ru.netflix.model.Film;
import ru.netflix.model.Rating;

public interface RatingService {
	Rating save(Rating rating);
	
	List<Film> getRatedFilmsByUserId(Long userId);
}
