package kb.moto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.scheduling.annotation.AsyncConfigurer;

@EnableCaching
@EnableR2dbcRepositories
@SpringBootApplication
public class Application implements AsyncConfigurer {
   public static void main(String[] args) {
      SpringApplication.run(Application.class, args);
   }
}
