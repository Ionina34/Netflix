package ru.netflix.service.interfaces;

import java.security.Principal;
import java.util.List;

import ru.netflix.model.Film;
import ru.netflix.model.Rating;

public interface RatingService {
	Rating save(Film film,int value,Principal principal);
	Rating save(Long filmId,int value,Principal principal);
	
	List<Rating> getRatingByUserIdAndFilmId(Long userId,Long filmId);
}
