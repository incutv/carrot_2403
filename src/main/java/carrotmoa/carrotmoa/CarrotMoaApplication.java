package carrotmoa.carrotmoa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CarrotMoaApplication {
    public static void main(String[] args) {
        SpringApplication.run(CarrotMoaApplication.class, args);
    }
}

