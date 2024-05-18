package com.sadhan.usermanagement.models;

import java.sql.Time;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.LastModifiedDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;
  private String email;
  private String password;
  private String role;
  private String status;

  @CreationTimestamp
  private Time created_at;
  @LastModifiedDate
  private Time updated_at;
  private Boolean deleted = false;
  private Time last_login_at = null;

  public User() {
  }

  public User(Long id, String name, String email, String password, String role, String status, Time created_at,
      Time updated_at, Boolean deleted, Time last_login_at) {
    super();
    this.id = id;
    this.name = name;
    this.email = email;
    this.password = password;
    this.role = role;
    this.status = status;
    this.created_at = created_at;
    this.updated_at = updated_at;
    this.deleted = deleted;
    this.last_login_at = last_login_at;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public Time getCreated_at() {
    return created_at;
  }

  public void setCreated_at(Time created_at) {
    this.created_at = created_at;
  }

  public Time getUpdated_at() {
    return updated_at;
  }

  public void setUpdated_at(Time updated_at) {
    this.updated_at = updated_at;
  }

  public Time getLast_login_at() {
    return last_login_at;
  }

  public Boolean isDeleted() {
    return deleted;
  }

  public void setDeleted(Boolean deleted) {
    this.deleted = deleted;
  }

  public void setLast_login_at(Time last_login_at) {
    this.last_login_at = last_login_at;
  }

}
