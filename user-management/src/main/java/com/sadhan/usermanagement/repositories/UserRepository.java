package com.sadhan.usermanagement.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sadhan.usermanagement.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

  Optional<User> findByEmail(String email);

}
