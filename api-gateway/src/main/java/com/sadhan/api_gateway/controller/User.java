package com.sadhan.api_gateway.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class User {

  @RequestMapping("api/v1/users")
  @GetMapping("/signup")
  public String signUp() {
    return "List of users";
  }

  @GetMapping("/login")
  public String login() {
    return "Login";
  }

}
