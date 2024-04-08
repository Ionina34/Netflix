package ru.netflix.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ServiceResponce<T> {
	private String status;
	private T data;
}
