package ru.netflix.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ru.netflix.model.Screenwriter;

@Repository
public interface ScreenwriterRepository extends JpaRepository<Screenwriter, Long>{
	Page<Screenwriter> findAll(Pageable pageable);
	
	Screenwriter findByName(String name);
	List<Screenwriter> findScreenwritersByFilmsId(Long screenwritersId);
	
	@Query("SELECT s FROM Screenwriter s "
			+"ORDER BY CASE WHEN :orderName = 'ASC' THEN s.name END ASC, "
			+"CASE WHEN :orderName = 'DESC' THEN s.name END DESC, "
			+"CASE WHEN :orderBirthday = 'ASC' THEN s.birthday END ASC, "
			+"CASE WHEN :orderBirthday = 'DESC' THEN s.birthday END DESC, "
			+"CASE WHEN :orederCreatedDate = 'ASC' THEN s.created_at END ASC, "
			+"CASE WHEN :orederCreatedDate = 'DESC' THEN s.created_at END DESC")
	Page<Screenwriter> getScreenwriterOrderByNameAndBirthdayAndCreatedDate(Pageable pageable,@Param("orderName") String name,
			@Param("orderBirthday") String birthday,@Param("orederCreatedDate") String created_date);
}
