package ru.netflix.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.netflix.model.Rating;
import ru.netflix.repository.RatingRepository;
import ru.netflix.service.interfaces.RatingService;

@Service
public class RatingServiceImpl implements RatingService {

	@Autowired
	private RatingRepository ratingRepository;
	
	@Override
	public Rating save(Rating rating) {
		return ratingRepository.save(rating);
	}

	@Override
	public List<Rating> getRatingByUserIdAndFilmId(Long userId, Long filmId) {
		return ratingRepository.getRatingByUserIdAndFilmId(userId, filmId);
	}
}
