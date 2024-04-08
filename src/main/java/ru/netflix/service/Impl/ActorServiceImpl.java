package ru.netflix.service.Impl;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ru.netflix.model.Actor;
import ru.netflix.repository.ActorRepository;
import ru.netflix.repository.FilmRepository;
import ru.netflix.service.ActorService;

@Service
@RequiredArgsConstructor
public class ActorServiceImpl implements ActorService{
	private final ActorRepository actorRepository;
	
	@Override
	public List<Actor> findActorsByFilmsId(Long filmId){
		return actorRepository.findActorsByFilmsId(filmId);
	}

	@Override
	public Actor findActorById(Long id) {
		return actorRepository.findById(id).orElse(null);
	}
}
