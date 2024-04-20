package ru.netflix.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.netflix.model.Director;
import ru.netflix.repository.DirectorRepository;
import ru.netflix.service.DirectorService;

@Service
public class DirectorServiceImpl implements DirectorService{
	@Autowired
	private DirectorRepository directorRepository;
	
	@Override 
	public List<Director> findDirectorsByFilmsId(Long filmId){
		return directorRepository.findDirectorsByFilmsId(filmId);
	}
}
