package ru.netflix.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.netflix.model.Screenwriter;
import ru.netflix.repository.ScreenwriterRepository;
import ru.netflix.service.interfaces.ScreenwriterService;

@Service
public class ScreenwriterServiceImpl implements ScreenwriterService {
	@Autowired
	private ScreenwriterRepository screenwriterRepository;
	
	@Override
	public List<Screenwriter> findScreenwritersByFilmsId(Long filmdId) {
		return screenwriterRepository.findScreenwritersByFilmsId(filmdId);
	}

}
