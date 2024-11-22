package com.fusion.service.impl;

import com.fusion.entity.Role;
import com.fusion.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

  private final UserRepository userRepository;

  @Override
  @Transactional(readOnly = true)
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	return this.userRepository.findByUsername(username)
		.map(user -> User.builder()
			.username(user.getUsername())
			.password(user.getPassword())
			.authorities(user.getRoles().stream()
				.map(Role::getRoleName)
				.map(role -> new SimpleGrantedAuthority(role.name()))
				.toList())
			.build())
		.orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
  }
}
