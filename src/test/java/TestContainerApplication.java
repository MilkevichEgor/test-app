import com.fusion.ServiceApplication;
import config.TestContainersConfiguration;
import org.springframework.boot.SpringApplication;

public class TestContainerApplication {
  public static void main(String[] args) {
	SpringApplication.from(ServiceApplication::main)
		.with(TestContainersConfiguration.class)
		.run();
  }
}
