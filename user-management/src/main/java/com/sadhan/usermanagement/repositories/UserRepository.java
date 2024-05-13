package com.sadhan.usermanagement.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sadhan.usermanagement.models.User;

public interface UserRepository extends JpaRepository<User, String> {

}
