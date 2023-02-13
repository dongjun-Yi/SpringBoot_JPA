package jpashop.jpapractice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class JpapracticeApplication {

	public static void main(String[] args) {
		SpringApplication.run(JpapracticeApplication.class, args);
	}

}
