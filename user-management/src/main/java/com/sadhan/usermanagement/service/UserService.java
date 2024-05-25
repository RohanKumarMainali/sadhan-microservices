package com.sadhan.usermanagement.service;

import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.sadhan.proto.CreateUserRequest;
import com.sadhan.proto.CreateUserResponse;
import com.sadhan.proto.UserServiceGrpc.UserServiceImplBase;
import com.sadhan.usermanagement.common.EnumMapper;
import com.sadhan.usermanagement.models.User;
import com.sadhan.usermanagement.models.User.UserRole;
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

    Logger logger = LoggerFactory.getLogger(UserService.class);
    String encodedPassword = passwordEncoder.encode(request.getPassword());
    EnumMapper em = new EnumMapper();
    User user = new User();
    user.setName(request.getName());
    user.setEmail(request.getEmail());
    user.setPassword(encodedPassword);
    user.setRole(UserRole.USER);
    user.setStatus(User.UserStatus.ACTIVE);
    try {
      // Save the user to the database
      userRepository.save(user);
    } catch (ConstraintViolationException e) {
      logger.error("User already exists");
      responseObserver
          .onNext(CreateUserResponse.newBuilder().setStatusCode(400).setMessage("User already exists").build());
      responseObserver.onCompleted();
      return;
    } catch (Exception e) {
      logger.error("Could not create user, Error: " + e.getMessage());
      responseObserver
          .onNext(CreateUserResponse.newBuilder().setStatusCode(500).setMessage("Error creating user").build());
      responseObserver.onCompleted();
      return;
    }
    logger.info("User created successfully");
    responseObserver
        .onNext(CreateUserResponse.newBuilder().setStatusCode(200).setMessage("User created successfully").build());
    responseObserver.onCompleted();
  }

}
