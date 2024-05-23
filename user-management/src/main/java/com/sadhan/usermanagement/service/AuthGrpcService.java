package com.sadhan.usermanagement.service;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import com.sadhan.proto.AuthServiceGrpc;
import com.sadhan.proto.JwtRequest;
import com.sadhan.proto.JwtResponse;
import com.sadhan.usermanagement.jwt.JwtAuthProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import io.grpc.stub.StreamObserver;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class AuthGrpcService extends AuthServiceGrpc.AuthServiceImplBase {

  private JwtAuthProvider jwtAuthProvider;

  public AuthGrpcService(JwtAuthProvider jwtAuthProvider) {
    this.jwtAuthProvider = jwtAuthProvider;
  }

  @Value("${jwt.secret.key}")
  String jwtSecretKey;

  @Override
  public void authenticate(JwtRequest request, StreamObserver<JwtResponse> responseObserver) {
    JwtResponse response;
    Logger logger = LoggerFactory.getLogger(AuthGrpcService.class);
    try {
      logger.info("Authenticating user");
      Authentication authenticate = jwtAuthProvider.authenticate(
          new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
      String authority = authenticate.getAuthorities().iterator().next().getAuthority();
      Instant now = Instant.now();
      Instant expiry = now.plus(1, ChronoUnit.HOURS);
      SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(jwtSecretKey));
      Map<String, String> claimMap = new HashMap<>();
      claimMap.put("email", authenticate.getName());
      claimMap.put("role", authority);
      String jwt = Jwts.builder().subject(request.getEmail())
          .claims(claimMap)
          .setIssuedAt(Date.from(now))
          .setExpiration(Date.from(expiry))
          .signWith(key).compact();

      response = JwtResponse.newBuilder().setStatusCode(200).setJwtToken(jwt).setMessage("User logged in successfully")
          .build();
      logger.info("User logged in successfully");
    } catch (BadCredentialsException | UsernameNotFoundException e) {
      logger.error("Invalid credentials");
      response = JwtResponse.newBuilder().setStatusCode(401).setError("Invalid credentials")
          .setMessage("Invalid credentials").build();
    } catch (Exception e) {
      logger.error("Internal server error");
      response = JwtResponse.newBuilder().setStatusCode(500).setError("Internal server error")
          .setMessage("Internal server error").build();
    }

    responseObserver.onNext(response);
    responseObserver.onCompleted();
  }
}
