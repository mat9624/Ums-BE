package com.example.pixeltek.DAO.configuration;

import com.example.pixeltek.DAO.repository.UserRepository;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = UserRepository.class)
public class DaoConfiguration {
}
