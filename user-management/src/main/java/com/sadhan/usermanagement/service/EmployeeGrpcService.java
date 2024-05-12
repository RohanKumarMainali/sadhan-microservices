package com.sadhan.usermanagement.service;

import com.google.protobuf.Empty;
import com.sadhan.proto.Employee;
import com.sadhan.proto.EmployeeServiceGrpc;

import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
class EmployeeGrpcService extends EmployeeServiceGrpc.EmployeeServiceImplBase {

  @Override
  public void getEmployeeInfo(Empty request, StreamObserver<Employee> responseObserver) {
    Employee employee = Employee.newBuilder().setId("1").setName("rohan kumar mainali").setEmail("rohan.test@test.com")
        .build();

    responseObserver.onNext(employee);
    responseObserver.onCompleted();
  }

}
