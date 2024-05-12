package com.sadhan.usermanagement.service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import com.sadhan.proto.AuthServiceGrpc;
import com.sadhan.proto.JwtRequest;
import com.sadhan.proto.JwtResponse;
import com.sadhan.usermanagement.jwt.JwtAuthProvider;
import org.springframework.security.core.Authentication;

import io.grpc.stub.StreamObserver;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
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
    Authentication authenticate = jwtAuthProvider.authenticate(
        new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

    String authority = authenticate.getAuthorities().iterator().next().getAuthority();

    Instant now = Instant.now();
    Instant expiry = now.plus(1, ChronoUnit.HOURS);

    // JwtResponse response = JwtResponse.newBuilder().setJwtToken(
    // Jwts.builder().setSubject("")
    // .claim("auth", authority)
    // .setIssuedAt(Date.from(now))
    // .setExpiration(Date.from(expiry))
    // .signWith(SignatureAlgorithm.HS512, jwtSecretKey)
    // .compact())
    // .build();

    JwtResponse newResponse = JwtResponse.newBuilder().setJwtToken("I am logged in").build();

    responseObserver.onNext(newResponse);
    responseObserver.onCompleted();

  }
}
