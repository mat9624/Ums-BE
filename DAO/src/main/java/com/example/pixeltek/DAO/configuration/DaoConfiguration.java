package com.example.pixeltek.DAO.configuration;

import com.example.pixeltek.DAO.repository.UserRepositoryI;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = UserRepositoryI.class)
public class DaoConfiguration {
}
