package ru.netflix.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ru.netflix.model.Actor;
import ru.netflix.model.Director;

@Repository
public interface DirectorRepository extends JpaRepository<Director,Long> {
	Page<Director> findAll(Pageable pageable);
	
	Director findByName(String name);
	List<Director> findDirectorsByFilmsId(Long directorId);
	
	@Query("SELECT d FROM Director d "
			+"ORDER BY CASE WHEN :orderName = 'ASC' THEN d.name END ASC, "
			+"CASE WHEN :orderName = 'DESC' THEN d.name END DESC, "
			+"CASE WHEN :orderBirthday = 'ASC' THEN d.birthday END ASC, "
			+"CASE WHEN :orderBirthday = 'DESC' THEN d.birthday END DESC, "
			+"CASE WHEN :orederCreatedDate = 'ASC' THEN d.created_at END ASC, "
			+"CASE WHEN :orederCreatedDate = 'DESC' THEN d.created_at END DESC")
	Page<Director> getDirectorsOrderByNameAndBirthdayAndCreatedDate(Pageable pageable,@Param("orderName") String name,
			@Param("orderBirthday") String birthday,@Param("orederCreatedDate") String created_date);
}
