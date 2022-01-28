package com.example.pixeltek.DAO.configuration;

import com.example.pixeltek.DAO.repository.IUserRepository;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = IUserRepository.class)
public class DaoConfiguration {
}
