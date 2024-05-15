package ru.netflix.service.Impl;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.netflix.model.Film;
import ru.netflix.model.Rating;
import ru.netflix.model.User;
import ru.netflix.repository.RatingRepository;
import ru.netflix.repository.UserRepository;
import ru.netflix.service.interfaces.RatingService;

@Service
public class RatingServiceImpl implements RatingService {

	@Autowired
	private RatingRepository ratingRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public Rating save(Rating rating) {
		return ratingRepository.save(rating);
	}
	
	@Override
	public List<Film> getRatedFilmsByUserId(Long userId) {
		User user = userRepository.findById(userId).get();
		return new ArrayList<Film>(user.getRatedFilms());
	}

}
