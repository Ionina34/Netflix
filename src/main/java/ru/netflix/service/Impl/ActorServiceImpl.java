package ru.netflix.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.netflix.model.Actor;
import ru.netflix.repository.ActorRepository;
import ru.netflix.service.ActorService;

@Service
public class ActorServiceImpl implements ActorService{
	@Autowired
	private ActorRepository actorRepository;
	
	@Override
	public List<Actor> findActorsByFilmsId(Long filmId){
		return actorRepository.findActorsByFilmsId(filmId);
	}

	@Override
	public Actor findActorById(Long id) {
		return actorRepository.findById(id).orElse(null);
	}
}
