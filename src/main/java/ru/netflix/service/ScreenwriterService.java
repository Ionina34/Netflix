package ru.netflix.service;

import java.util.List;

import ru.netflix.model.Screenwriter;

public interface ScreenwriterService {
	List<Screenwriter> findScreenwritersByFilmsId(Long filmdId);
}	
