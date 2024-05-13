package com.sadhan.usermanagement.models;

import java.sql.Time;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private String id;
  private String name;
  private String email;
  private String password;
  private String role;
  private String status;
  private Time created_at;
  private Time updated_at;
  private Time deleted_at;
  private Time last_login_at;

  public User() {
  }

  public User(String id, String name, String email, String password, String role, String status, Time created_at,
      Time updated_at, Time deleted_at, Time last_login_at) {
    this.id = id;
    this.name = name;
    this.email = email;
    this.password = password;
    this.role = role;
    this.status = status;
    this.created_at = created_at;
    this.updated_at = updated_at;
    this.deleted_at = deleted_at;
    this.last_login_at = last_login_at;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
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

  public Time getDeleted_at() {
    return deleted_at;
  }

  public void setDeleted_at(Time deleted_at) {
    this.deleted_at = deleted_at;
  }

  public Time getLast_login_at() {
    return last_login_at;
  }

  public void setLast_login_at(Time last_login_at) {
    this.last_login_at = last_login_at;
  }

}
