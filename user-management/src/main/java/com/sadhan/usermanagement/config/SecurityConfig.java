package com.sadhan.usermanagement.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.vote.UnanimousBased;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.core.Authentication;
import com.sadhan.proto.EmployeeServiceGrpc;
import com.sadhan.usermanagement.jwt.JwtAuthProvider;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import net.devh.boot.grpc.server.security.authentication.BearerAuthenticationReader;
import net.devh.boot.grpc.server.security.authentication.GrpcAuthenticationReader;
import net.devh.boot.grpc.server.security.check.AccessPredicate;
import net.devh.boot.grpc.server.security.check.AccessPredicateVoter;
import net.devh.boot.grpc.server.security.check.GrpcSecurityMetadataSource;
import net.devh.boot.grpc.server.security.check.ManualGrpcSecurityMetadataSource;

@Configuration
public class SecurityConfig {

  private final JwtAuthProvider jwtAuthProvider;

  @Value("${jwt.secret.key}")
  String jwtSecretKey;

  public SecurityConfig(JwtAuthProvider jwtAuthProvider) {
    this.jwtAuthProvider = jwtAuthProvider;
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  AuthenticationManager authenticationManager() {
    jwtAuthProvider.setPasswordEncoder(passwordEncoder());
    return new ProviderManager(jwtAuthProvider);
  }

  @Bean
  GrpcAuthenticationReader grpcAuthenticationReader() {

    return new BearerAuthenticationReader(token -> {
      SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(jwtSecretKey));
      Jws<Claims> claims = Jwts.parser().verifyWith(key).build().parseSignedClaims(token);
      List<SimpleGrantedAuthority> authorities = Arrays.stream(
          claims.getPayload().get("role").toString().split(",")).map(SimpleGrantedAuthority::new)
          .collect(Collectors.toList());
      System.out.println("Authorities: " + authorities);
      Authentication authentication = new UsernamePasswordAuthenticationToken(
          new User(claims.getPayload().getSubject(), token, authorities),
          token,
          authorities);

      return authentication;
    });

  }

  @Bean
  GrpcSecurityMetadataSource grpcSecurityMetadataSource() {
    ManualGrpcSecurityMetadataSource source = new ManualGrpcSecurityMetadataSource();
    source.setDefault(AccessPredicate.permitAll());
    source.set(EmployeeServiceGrpc.getGetEmployeeInfoMethod(), AccessPredicate.hasRole("admin"));
    return source;
  }

  @Bean
  AccessDecisionManager accessDecisionManager() {
    List<AccessDecisionVoter<?>> accessDecisionVoters = new ArrayList<>();
    accessDecisionVoters.add(new AccessPredicateVoter());
    return new UnanimousBased(accessDecisionVoters);
  }

}
