package com.fusion.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfiguration {

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	http.authorizeHttpRequests(authorizeHttpRequests ->
		authorizeHttpRequests
			.requestMatchers("/admin/**").hasRole("ADMIN")
			.anyRequest().authenticated());
//	http.sessionManagement(sessionManagement ->
//		sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
	http.formLogin(Customizer.withDefaults());
	http.httpBasic(Customizer.withDefaults());
	http.csrf(AbstractHttpConfigurer::disable);
	return http.build();
  }
}