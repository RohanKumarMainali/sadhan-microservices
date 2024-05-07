package com.sadhan.usermanagement.service;

import com.sadhan.proto.AuthServiceGrpc;
import com.sadhan.proto.JwtRequest;
import com.sadhan.proto.JwtResponse;

import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class AuthGrpcService extends AuthServiceGrpc.AuthServiceImplBase {

  @Override
  public void authenticate(JwtRequest request, StreamObserver<JwtResponse> responseObserver) {
    responseObserver.onNext(JwtResponse.newBuilder().setJwtToken("Bearer " + request.getUsername()).build());

    responseObserver.onCompleted();
  }
}
