package ru.alan.viewPerson.config;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Cookie;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.alan.viewPerson.exception.ResourceNotFoundException;

import java.io.IOException;

public class CoockieAuthFilter extends OncePerRequestFilter {

	private final StringRedisTemplate redisTemplate;

	public CoockieAuthFilter( StringRedisTemplate redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				String cookieValue = cookie.getValue();
				if (redisTemplate.opsForValue().get(cookieValue) != null) {
					try {
						Authentication authentication = coockieProvider.getAuthentication(cookieValue);
						SecurityContextHolder.getContext().setAuthentication(authentication);
					} catch (ResourceNotFoundException ignored) {

					}
				}
			}
		}
		filterChain.doFilter(request, response);
	}
}

