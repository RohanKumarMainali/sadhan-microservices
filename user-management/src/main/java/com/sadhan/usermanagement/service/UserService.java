package com.sadhan.usermanagement.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.sadhan.proto.CreateUserRequest;
import com.sadhan.proto.CreateUserResponse;
import com.sadhan.proto.UserServiceGrpc.UserServiceImplBase;
import com.sadhan.usermanagement.models.User;
import com.sadhan.usermanagement.repositories.UserRepository;

import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
class UserService extends UserServiceImplBase {

  @Autowired
  private UserRepository userRepository;

  @Override
  public void createUser(CreateUserRequest request, StreamObserver<CreateUserResponse> responseObserver) {

    // Save the user to the database
    User user = new User();
    user.setName(request.getName());
    user.setEmail(request.getEmail());
    user.setPassword(request.getPassword());
    user.setRole(request.getRole());
    user.setStatus(request.getStatus());
    User userResponse = userRepository.save(user);
    responseObserver.onNext(CreateUserResponse.newBuilder().setMessage("User created successfully").build());
    responseObserver.onCompleted();
  }

}
