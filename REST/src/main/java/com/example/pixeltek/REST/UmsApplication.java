package com.example.pixeltek.REST;



import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
@EnableJpaRepositories("com.example.pixeltek.DAO.repository")
@ComponentScan(basePackages = ("com.example.pixeltek"))
@EntityScan("com.example.pixeltek.DTO.model")
@ConfigurationPropertiesScan("com.example.pixeltek.REST.configuration")
@SpringBootApplication
public class UmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(UmsApplication.class, args);
    }
}
