package ma.fstt.backend_empolyee;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan("ma.fstt")
@EnableJpaRepositories("ma.fstt.backend_empolyee.repository")
@EntityScan("ma.fstt.backend_empolyee.entities")
public class BackendEmpolyeeApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendEmpolyeeApplication.class, args);
    }

}
