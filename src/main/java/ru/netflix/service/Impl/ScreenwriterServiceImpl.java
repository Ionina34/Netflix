package ru.netflix.service.Impl;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ru.netflix.model.Screenwriter;
import ru.netflix.repository.ScreenwriterRepository;
import ru.netflix.service.ScreenwriterService;

@Service
@RequiredArgsConstructor
public class ScreenwriterServiceImpl implements ScreenwriterService {
	private final ScreenwriterRepository screenwriterRepository;
	
	@Override
	public List<Screenwriter> findScreenwritersByFilmsId(Long filmdId) {
		return screenwriterRepository.findScreenwritersByFilmsId(filmdId);
	}

}
