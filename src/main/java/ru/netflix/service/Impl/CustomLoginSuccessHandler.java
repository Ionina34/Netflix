package ru.netflix.service.Impl;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler{

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		String redirectUrl="/films";//URL по умолчанию
		
		//Проверяем роли пользователя
		for(GrantedAuthority authority:authentication.getAuthorities()) {
			if("ROLE_ADMIN".equals(authority.getAuthority())) {
				redirectUrl="/admin/films";//URL ля администратора
				break;
			}
		}
		
		// Перенаправляем пользователя на соответствующую страницу
        response.sendRedirect(redirectUrl);
	}

}
