package config;

import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.testcontainers.containers.PostgreSQLContainer;

@Configuration
public class TestContainersConfiguration {

  @Bean
  @ServiceConnection
  public PostgreSQLContainer<?> postgreSQLContainer(DynamicPropertyRegistry registry) {
	var container = new PostgreSQLContainer<>("postgres:17");
	registry.add("postgresql.driver", container::getDriverClassName);
	return container;
  }
}
