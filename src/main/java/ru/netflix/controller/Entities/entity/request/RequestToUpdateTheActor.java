package ru.netflix.controller.entities.entity.request;

import java.util.List;

import lombok.Data;
import ru.netflix.model.Actor;
import ru.netflix.model.Film;

@Data
public class RequestToUpdateTheActor {
	Long actorId;
	Actor actor;
	List<Film> films;
}