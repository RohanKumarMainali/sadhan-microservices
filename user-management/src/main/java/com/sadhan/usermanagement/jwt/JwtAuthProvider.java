package com.sadhan.usermanagement.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.sadhan.usermanagement.service.MyUserDetailService;

@Component
public class JwtAuthProvider implements AuthenticationProvider {

  @Autowired
  private MyUserDetailService userDetailsService;

  private PasswordEncoder passwordEncoder;

  public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    if (!authentication.isAuthenticated()) {
      String email = authentication.getName();
      String password = authentication.getCredentials().toString();
      UserDetails userDetails = userDetailsService.loadUserByUsername(email);
      if (passwordEncoder.matches(password, userDetails.getPassword())) {
        return new UsernamePasswordAuthenticationToken(userDetails, null,
            userDetails.getAuthorities());
      }
      throw new BadCredentialsException("Invalid credential");
    }
    return authentication;
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return authentication.equals(UsernamePasswordAuthenticationToken.class);
  }

}
