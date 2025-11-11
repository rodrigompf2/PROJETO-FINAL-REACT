package br.com.serratec.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import br.com.serratec.security.JwtAuthenticationFilter;
import br.com.serratec.security.JwtAuthorizationFilter;
import br.com.serratec.security.JwtUtil;

@EnableWebSecurity
@Configuration
public class AppConfig {

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private UserDetailsService userDetailsService;

	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		AuthenticationManager authManager = authenticationManager(http.getSharedObject(AuthenticationConfiguration.class));
		
		JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(authManager, jwtUtil);
		jwtAuthenticationFilter.setRequiresAuthenticationRequestMatcher(
			new AntPathRequestMatcher("/auth/login", "POST")
		);
		
		JwtAuthorizationFilter jwtAuthorizationFilter = new JwtAuthorizationFilter(authManager, jwtUtil, userDetailsService);

		http.csrf(csrf -> csrf.disable()).cors(cors -> cors.configurationSource(corsConfigurationSource()))
				.authorizeHttpRequests(requests -> requests
						.requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
						.requestMatchers(HttpMethod.POST, "/clientes").permitAll() 
						.requestMatchers(HttpMethod.GET, "/funcionarios").permitAll()
						.requestMatchers(HttpMethod.POST, "/perfis").permitAll().requestMatchers("/h2-console/**")
						.permitAll()
						.requestMatchers(HttpMethod.POST, "/usuarios").permitAll()
						.requestMatchers(HttpMethod.GET, "/usuarios").hasRole("ADMIN")
						.requestMatchers(HttpMethod.GET, "/funcionarios/*/foto").hasAnyRole("ADMIN", "USER", "RH")
						.requestMatchers(HttpMethod.POST, "/funcionarios").hasAnyRole("ADMIN", "USER", "RH")
						.anyRequest().authenticated())
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.headers(headers -> headers.frameOptions().disable());

		http.addFilter(jwtAuthenticationFilter);
		http.addFilter(jwtAuthorizationFilter);

		return http.build();
	}

	@Bean
	BCryptPasswordEncoder criptografar() { 
		return new BCryptPasswordEncoder();
	}

	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
			throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(Arrays.asList("http://localhost:5173", "http://localhost:2000"));
		configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
		configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
		
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}

}