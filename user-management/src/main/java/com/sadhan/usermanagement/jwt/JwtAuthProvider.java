package com.sadhan.usermanagement.jwt;

import java.util.List;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthProvider implements AuthenticationProvider {

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    if (!authentication.isAuthenticated()) {
      UserDetails userDetails = new DomainUserDetails().loadUserByUsername(authentication.getName());
      return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
    return authentication;
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return authentication.equals(UsernamePasswordAuthenticationToken.class);
  }

  private static class DomainUserDetails implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
      String role = username.equals("rohan") ? "ROLE_ADMIN" : "ROLE_USER";
      return new User(username, "",
          List.of(new SimpleGrantedAuthority(role)));
    }

  }

}
