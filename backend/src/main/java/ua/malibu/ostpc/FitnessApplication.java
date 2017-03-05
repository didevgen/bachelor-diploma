package ua.malibu.ostpc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
public class FitnessApplication {
    public static void main(String[] args) {
        SpringApplication.run(FitnessApplication.class, args);
    }
}
