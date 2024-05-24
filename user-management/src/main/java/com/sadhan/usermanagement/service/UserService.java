package com.sadhan.usermanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.sadhan.proto.CreateUserRequest;
import com.sadhan.proto.CreateUserResponse;
import com.sadhan.proto.UserServiceGrpc.UserServiceImplBase;
import com.sadhan.usermanagement.common.EnumMapper;
import com.sadhan.usermanagement.models.User;
import com.sadhan.usermanagement.repositories.UserRepository;

import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
class UserService extends UserServiceImplBase {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Override
  public void createUser(CreateUserRequest request, StreamObserver<CreateUserResponse> responseObserver) {

    String encodedPassword = passwordEncoder.encode(request.getPassword());
    // Save the user to the database
    EnumMapper em = new EnumMapper();
    User user = new User();
    user.setName(request.getName());
    user.setEmail(request.getEmail());
    user.setPassword(encodedPassword);
    user.setRole(em.mapProtoRoleToJava(request.getRole()));
    user.setStatus(em.mapProtoStatusToJava(request.getStatus()));
    User userResponse = userRepository.save(user);
    responseObserver.onNext(CreateUserResponse.newBuilder().setMessage("User created successfully").build());
    responseObserver.onCompleted();
  }

}
