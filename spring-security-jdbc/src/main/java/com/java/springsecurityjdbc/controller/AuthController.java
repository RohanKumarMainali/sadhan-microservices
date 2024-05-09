package com.java.springsecurityjdbc.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

  @GetMapping("/")
  public String home() {
    return ("<h1>Welcome Home</h1>");
  }

  @GetMapping("/users")
  public String user() {
    return ("<h1>Welcome User</h1>");
  }

}
