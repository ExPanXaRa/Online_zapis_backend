//package ru.alan.viewPerson.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Lazy;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Service;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import ru.alan.viewPerson.service.impl.UserServiceImpl;
//
//@Service
//public class CoockieProvider {
//	private final UserDetailsService userDetailsService;
//
//	private final UserServiceImpl userService;
//
//	@Lazy
//	public CoockieProvider(UserDetailsService userDetailsService, UserServiceImpl userService) {
//		this.userDetailsService = userDetailsService;
//		this.userService = userService;
//	}
//
//	public Authentication getAuthentication(String coockieValue) {
//		UserDetails userDetails = userDetailsService.loadUserByUsername(coockieValue);
//		return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
//	}
//}
