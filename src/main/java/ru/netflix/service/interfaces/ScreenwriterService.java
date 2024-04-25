package ru.netflix.service.interfaces;

import java.util.List;

import ru.netflix.model.Screenwriter;

public interface ScreenwriterService {
	List<Screenwriter> findScreenwritersByFilmsId(Long filmdId);
}	
