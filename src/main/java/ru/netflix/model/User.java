package ru.netflix.model;

import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Table(name="users")
@AllArgsConstructor
@NoArgsConstructor
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(unique = true,name="name")
	private String name;
	//(unique = true,name="email")
	@Column(unique = true,name="email")
	private String email;
	private String password;
	private String roles;
	
	private LocalDate created_at;
	private LocalDate updated_at;
}
