package ru.alan.viewPerson.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig  {
	private final StringRedisTemplate stringRedisTemplate;
	private final CoockieProvider coockieProvider;

	public SecurityConfig(StringRedisTemplate stringRedisTemplate, CoockieProvider coockieProvider) {
		this.stringRedisTemplate = stringRedisTemplate;
		this.coockieProvider = coockieProvider;
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}


	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		return http
				.httpBasic(AbstractHttpConfigurer::disable)
				.cors(Customizer.withDefaults())
				.csrf(AbstractHttpConfigurer::disable)
				.sessionManagement(sessionManagement ->
						sessionManagement
								.sessionCreationPolicy(
										SessionCreationPolicy.STATELESS
								)
				)
				.authorizeHttpRequests((authz) ->
						authz
								.requestMatchers("/auth/register", "/auth/login", "/auth/resetPassword").permitAll()
								.anyRequest().authenticated()
				)
				.addFilterBefore(new CoockieAuthFilter(coockieProvider, stringRedisTemplate), UsernamePasswordAuthenticationFilter.class)
				.build();
	}

	@Bean
	public PasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
