package com.fusion.service;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootTest
public class ServiceTestApp {

  public static void main(String[] args) {
	System.out.println(new BCryptPasswordEncoder()
		.encode("123qwe"));
  }
}
