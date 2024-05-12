package com.sadhan.usermanagement.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.vote.UnanimousBased;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.sadhan.proto.EmployeeServiceGrpc;
import com.sadhan.usermanagement.jwt.JwtAuthProvider;

import net.devh.boot.grpc.server.security.authentication.BearerAuthenticationReader;
import net.devh.boot.grpc.server.security.authentication.GrpcAuthenticationReader;
import net.devh.boot.grpc.server.security.check.AccessPredicate;
import net.devh.boot.grpc.server.security.check.AccessPredicateVoter;
import net.devh.boot.grpc.server.security.check.GrpcSecurityMetadataSource;
import net.devh.boot.grpc.server.security.check.ManualGrpcSecurityMetadataSource;

@Configuration

public class SecurityConfig {

  private final JwtAuthProvider jwtAuthProvider;

  public SecurityConfig(JwtAuthProvider jwtAuthProvider) {
    this.jwtAuthProvider = jwtAuthProvider;
  }

  @Bean
  AuthenticationManager authenticationManager() {
    return new ProviderManager(jwtAuthProvider);
  }

  @Bean
  GrpcAuthenticationReader grpcAuthenticationReader() {
    return new BearerAuthenticationReader(token -> {
      System.out.println("Token: " + token);
      if (token.equals("rohan")) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        return new UsernamePasswordAuthenticationToken(new User("rohan", "", authorities), "", authorities);
      }

      List<GrantedAuthority> authorities = new ArrayList<>();
      authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
      return new UsernamePasswordAuthenticationToken(new User("rohan", "", authorities), "", authorities);
    });

  }

  @Bean
  GrpcSecurityMetadataSource grpcSecurityMetadataSource() {
    ManualGrpcSecurityMetadataSource source = new ManualGrpcSecurityMetadataSource();
    source.setDefault(AccessPredicate.permitAll());
    source.set(EmployeeServiceGrpc.getGetEmployeeInfoMethod(), AccessPredicate.hasRole("ROLE_ADMIN"));
    return source;
  }

  @Bean
  AccessDecisionManager accessDecisionManager() {
    List<AccessDecisionVoter<?>> accessDecisionVoters = new ArrayList<>();
    accessDecisionVoters.add(new AccessPredicateVoter());
    return new UnanimousBased(accessDecisionVoters);

  }
}