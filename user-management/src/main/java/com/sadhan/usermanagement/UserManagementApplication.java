package com.sadhan.usermanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.sadhan.usermanagement.repositories.UserRepository;

@SpringBootApplication
@EnableAutoConfiguration
@EnableJpaRepositories(basePackageClasses = UserRepository.class)
public class UserManagementApplication {

  public static void main(String[] args) {
    SpringApplication.run(UserManagementApplication.class, args);
  }

}
