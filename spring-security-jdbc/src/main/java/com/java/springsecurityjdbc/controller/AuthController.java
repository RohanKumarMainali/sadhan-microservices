package com.java.springsecurityjdbc.controller;

import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

  @GetMapping("/")
  public String home() {
    return ("<h1>Welcome Home</h1>");
  }

  @GetMapping("/users")
  public String user(Authentication authentication) {
    return ("<h1>Welcome User</h1> " + getName(authentication));
  }

  private String getName(Authentication authentication) {
    return Optional.of(authentication.getPrincipal())
        .filter(OidcUser.class::isInstance)
        .map(OidcUser.class::cast)
        .map(OidcUser::getEmail)
        .orElse(authentication.getName());
  }

}
