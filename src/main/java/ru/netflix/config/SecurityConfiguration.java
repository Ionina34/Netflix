package ru.netflix.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import ru.netflix.service.Impl.CustomLoginSuccessHandler;
import ru.netflix.service.Impl.MyUserDetailsService;

@Configuration
public class SecurityConfiguration {
	@Bean
	public UserDetailsService userDetailsService() {
		return new MyUserDetailsService();
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http.csrf(AbstractHttpConfigurer::disable)
				.authorizeHttpRequests(auth -> auth.requestMatchers("films").permitAll()
						.requestMatchers("registration").anonymous()
						.requestMatchers("/films/{id}").permitAll()
						.requestMatchers("/films/search").permitAll()
						.requestMatchers("/films/search/**").permitAll()
						.requestMatchers("/films/all/get").permitAll()
						.requestMatchers("/films/sort").permitAll()
						.requestMatchers("/actor/{id}").permitAll()
						.requestMatchers("/admin/**").hasRole("ADMIN")
						.requestMatchers("/admin/films/update").hasRole("ADMIN")
						.anyRequest().hasRole("USER"))
				.formLogin(form -> form
						.loginPage("/login")
						.successHandler(new CustomLoginSuccessHandler())
						.permitAll()
					)
				.build();
	}

	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		return (web) -> web.ignoring().requestMatchers("/images/**", "/js/**","/js/admin/**", "/css/**", "/webjars/**", "/logo/**");
	}

	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(userDetailsService());
		provider.setPasswordEncoder(passwordEncoder());
		return provider;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
