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

import ru.netflix.controller.entities.entity.request.RequestToAddAActor;
import ru.netflix.controller.entities.entity.request.RequestToUpdateTheActor;
import ru.netflix.model.Actor;
import ru.netflix.service.interfaces.ActorService;

@RestController
/** Контроллер администратора для работы с актерами */
public class AdminActorRestController {
	@Autowired
	private ActorService actorService;
	
	/** Метод для получения все актеров
	 *  @param pageable - для пагинации*/
	@GetMapping("/admin/actors/get")
	public ResponseEntity<Page<Actor>> getAllActor(Pageable pageable){
		return ResponseEntity.ok(actorService.findAllActors(pageable));
	}
	
	/** Метод для обновления актера
	 * @param data - данные для обновления
	 * (id актера, новая инф-ия, список фильмов)*/
	@PutMapping("/admin/actors/update")
	public ResponseEntity<Actor> updateActor(@RequestBody RequestToUpdateTheActor data){
		actorService.updateActor(data.getActorId(), data.getActor());
		
		Actor actor=actorService.findActorById(data.getActorId());
		actorService.updateActorFilms(data.getFilms(), actor);
		
		return ResponseEntity.ok(actor);
	}
	
	@PostMapping("/admin/actors/add")
	/** Метод для добавления актера
	 * @param data - данные для добавления (инф-ия об актере, список фильмов) */
	public ResponseEntity<Actor> addActor(@RequestBody RequestToAddAActor data){
		actorService.saveActor(data.getActor());
		
		Actor actor=actorService.findByName(data.getActor().getName());
		actorService.addActorFilms(data.getFilms(), actor);
		
		return ResponseEntity.ok(actor);
	}
	
	/** Метод дляя удаления актера
	 * @param id - id удаляемого актера */
	@DeleteMapping("/admin/actors/delete/{id}")
	public ResponseEntity<Long> deleteActor(@PathVariable Long id) {
	    actorService.delete(id);
	    
	    return  ResponseEntity.ok(id);
	}
	
	/** Метод для сортировки актеров
	 * @param pageable - для пагинации
	 * @param data - дата рождения
	 * @param alphabet - по алфавиту
	 * @param created - дата создания записи в бд*/
	@GetMapping("/admin/actors/sort")
	public Page<Actor> actorsSort(Pageable pageable, @RequestParam(name="data") String data,
			@RequestParam(name = "alphabet") String alphabet, @RequestParam(name = "created") String created){
		return actorService.sort(pageable, alphabet,data, created);
	}
}
