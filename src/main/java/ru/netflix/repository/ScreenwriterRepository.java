package ru.netflix.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.netflix.model.Screenwriter;

public interface ScreenwriterRepository extends JpaRepository<Screenwriter, Long>{
	List<Screenwriter> findScreenwritersByFilmsId(Long screenwritersId);
}
