package ru.netflix.service.Impl;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ru.netflix.model.Director;
import ru.netflix.repository.DirectorRepository;
import ru.netflix.service.DirectorService;

@Service
@RequiredArgsConstructor
public class DirectorServiceImpl implements DirectorService{
	private final  DirectorRepository directorRepository;
	
	@Override 
	public List<Director> findDirectorsByFilmsId(Long filmId){
		return directorRepository.findDirectorsByFilmsId(filmId);
	}
}
