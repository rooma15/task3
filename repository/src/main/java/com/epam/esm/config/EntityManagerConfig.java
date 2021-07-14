package com.epam.esm.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@Configuration
@EnableTransactionManagement
public class EntityManagerConfig {
    @Bean(name = "entityManager")
    @Primary
     EntityManager getEntityManager(){
        String PERSISTENCE_UNIT_NAME = "my-pers";
        EntityManagerFactory emFactoryObj = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        return emFactoryObj.createEntityManager();
    }
}
