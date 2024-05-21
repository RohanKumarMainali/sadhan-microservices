package com.sadhan.usermanagement.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.sadhan.usermanagement.service.MyUserDetailService;

@Component
public class JwtAuthProvider implements AuthenticationProvider {

  @Autowired
  private MyUserDetailService userDetailsService;

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    if (!authentication.isAuthenticated()) {
      UserDetails userDetails = userDetailsService.loadUserByUsername(authentication.getName());
      return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
    return authentication;
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return authentication.equals(UsernamePasswordAuthenticationToken.class);
  }

}
