package com.vgowda.hasura.chinook;

import javax.persistence.EntityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ChinookApp {
  private static final Logger logger = LoggerFactory.getLogger(ChinookApp.class);

  private final EntityManager entityManager;

  @Autowired
  public ChinookApp(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  public static void main(String[] args) {
    SpringApplication.run(ChinookApp.class, args);
  }

}
