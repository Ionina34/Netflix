package ru.netflix.controller.API;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ru.netflix.controller.entities.entity.request.RequestToAddADirector;
import ru.netflix.controller.entities.entity.request.RequestToUpdateTheDirector;
import ru.netflix.model.Director;
import ru.netflix.service.interfaces.DirectorService;

@RestController
public class AdminDirectorRestController {
	@Autowired
	private DirectorService directorService;
	
	@GetMapping("/admin/directors/get")
	public ResponseEntity<Page<Director>> getAllDirectors(Pageable pageable){
		return ResponseEntity.ok(directorService.findAllDirectors(pageable));
	}
	
	@PutMapping("/admin/directors/update")
	public ResponseEntity<Director> updateDirector(@RequestBody RequestToUpdateTheDirector data){
		directorService.updateDirector(data.getDirectorId(), data.getDirector());
		
		Director director=directorService.findDirectorById(data.getDirectorId());
		directorService.updateDirectorFilms(data.getFilms(), director);
		
		return ResponseEntity.ok(director);
	}
	
	@PostMapping("/admin/directors/add")
	public ResponseEntity<Director> addDirector(@RequestBody RequestToAddADirector data){
		directorService.saveDirector(data.getDirector());
		
		Director director=directorService.findByName(data.getDirector().getName());
		directorService.addDirectorFilms(data.getFilms(), director);
		
		return ResponseEntity.ok(director);
	}
	
	@DeleteMapping("/admin/directors/delete/{id}")
	public ResponseEntity<Long> deleteDirector(@PathVariable Long id) {
	    directorService.delete(id);
	    
	    return  ResponseEntity.ok(id);
	}
	
	@GetMapping("/admin/directors/sort")
	public Page<Director> directorsSort(Pageable pageable, @RequestParam(name="data") String data,
			@RequestParam(name = "alphabet") String alphabet, @RequestParam(name = "created") String created){
		return directorService.sort(pageable, alphabet,data, created);
	}
}