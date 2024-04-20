package ru.netflix.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ru.netflix.model.Screenwriter;

@Repository
public interface ScreenwriterRepository extends JpaRepository<Screenwriter, Long>{
	List<Screenwriter> findScreenwritersByFilmsId(Long screenwritersId);
}
