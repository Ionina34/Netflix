package ru.netflix.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Table(name="users")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(unique = true,name="name")
	private String name;
	//(unique = true,name="email")
	//private String email;
	private String password;
	private String roles;
}
