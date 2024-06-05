package ru.netflix.controller.entities.entity.request;

import java.util.List;

import lombok.Data;
import ru.netflix.model.Film;
import ru.netflix.model.Screenwriter;

@Data
public class RequestToUpdateTheScreenwriter {
	Long screenwriterId;
	Screenwriter screenwriter;
	List<Film> films;
}
