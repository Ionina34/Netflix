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

import ru.netflix.controller.entities.entity.request.RequestToAddAScreenwriter;
import ru.netflix.controller.entities.entity.request.RequestToUpdateTheScreenwriter;
import ru.netflix.model.Screenwriter;
import ru.netflix.service.interfaces.ScreenwriterService;

@RestController
public class AdminScreenwriterRestController {
	@Autowired
	private ScreenwriterService screenwriterService;
	
	@GetMapping("/admin/screenwriters/get")
	public ResponseEntity<Page<Screenwriter>> getAllScreenwriters(Pageable pageable){
		return ResponseEntity.ok(screenwriterService.findAllScreenwriters(pageable));
	}
	
	@PutMapping("/admin/screenwriters/update")
	public ResponseEntity<Screenwriter> updateScreenwriter(@RequestBody RequestToUpdateTheScreenwriter data){
		screenwriterService.updateScreenwriter(data.getScreenwriterId(), data.getScreenwriter());
		
		Screenwriter screenwriter=screenwriterService.findScreenwriterById(data.getScreenwriterId());
		screenwriterService.updateScreenwriterFilms(data.getFilms(), screenwriter);
		
		return ResponseEntity.ok(screenwriter);
	}
	
	@PostMapping("/admin/screenwriters/add")
	public ResponseEntity<Screenwriter> addScreenwriter(@RequestBody RequestToAddAScreenwriter data){
		screenwriterService.saveScreenwriter(data.getScreenwriter());
		
		Screenwriter screenwriter=screenwriterService.findByName(data.getScreenwriter().getName());
		screenwriterService.addScreenwriterFilms(data.getFilms(), screenwriter);
		
		return ResponseEntity.ok(screenwriter);
	}
	
	@DeleteMapping("/admin/screenwriters/delete/{id}")
	public ResponseEntity<Long> deleteScreenwriter(@PathVariable Long id) {
	    screenwriterService.delete(id);
	    
	    return  ResponseEntity.ok(id);
	}
	
	@GetMapping("/admin/screenwriters/sort")
	public Page<Screenwriter> screenwritersSort(Pageable pageable, @RequestParam(name="data") String data,
			@RequestParam(name = "alphabet") String alphabet, @RequestParam(name = "created") String created){
		return screenwriterService.sort(pageable, alphabet,data, created);
	}
}
