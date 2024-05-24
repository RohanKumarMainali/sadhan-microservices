package com.sadhan.usermanagement.models;

import java.time.LocalDateTime;

import org.checkerframework.common.aliasing.qual.Unique;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.LastModifiedDate;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {

  public enum UserRole {
    ADMIN("admin"), USER("user"), HOST("host");

    public int[] split(String string) {
      return null;
    }

    private final String displayName;

    UserRole(String displayName) {
      this.displayName = displayName;
    }

    public String getDisplayName() {
      return displayName;
    }
  }

  public enum UserStatus {
    ACTIVE("active"), INACTIVE("inactive"), PENDING("pending");

    private final String displayName;

    UserStatus(String displayName) {
      this.displayName = displayName;
    }

    public String getDisplayName() {
      return displayName;
    }
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;
  @Column(unique = true)
  private String email;
  private String password;

  @Enumerated(EnumType.STRING)
  private UserRole role;

  @Enumerated(EnumType.STRING)
  private UserStatus status;

  @CreationTimestamp
  private LocalDateTime created_at;
  @LastModifiedDate
  private LocalDateTime updated_at;
  private Boolean deleted = false;
  private LocalDateTime last_login_at = null;

  public User() {
  }

  public User(Long id, String name, String email, String password, UserRole role, UserStatus status,
      LocalDateTime created_at,
      LocalDateTime updated_at, Boolean deleted, LocalDateTime last_login_at) {
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

  public UserRole getRole() {
    return role;
  }

  public void setRole(UserRole role) {
    this.role = role;
  }

  public UserStatus getStatus() {
    return status;
  }

  public void setStatus(UserStatus status) {
    this.status = status;
  }

  public LocalDateTime getCreated_at() {
    return created_at;
  }

  public void setCreated_at(LocalDateTime created_at) {
    this.created_at = created_at;
  }

  public LocalDateTime getUpdated_at() {
    return updated_at;
  }

  public void setUpdated_at(LocalDateTime updated_at) {
    this.updated_at = updated_at;
  }

  public LocalDateTime getLast_login_at() {
    return last_login_at;
  }

  public Boolean isDeleted() {
    return deleted;
  }

  public void setDeleted(Boolean deleted) {
    this.deleted = deleted;
  }

  public void setLast_login_at(LocalDateTime last_login_at) {
    this.last_login_at = last_login_at;
  }

}
