package com.epam.training.homework.familybank.spring;

import com.epam.training.homework.familybank.service.PopulateDatabaseService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfigurationService {

    @Bean(initMethod = "populateDatabase")
    public PopulateDatabaseService populateDatabaseService() {
        return new PopulateDatabaseService();
    }
}
