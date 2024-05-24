package com.sadhan.usermanagement.common;

import com.sadhan.proto.Role;
import com.sadhan.proto.Status;
import com.sadhan.usermanagement.models.User.UserRole;
import com.sadhan.usermanagement.models.User.UserStatus;;

// Utility class for mapping
public class EnumMapper {
  public UserRole mapProtoRoleToJava(Role protoRole) {
    switch (protoRole) {
      case ADMIN:
        return UserRole.ADMIN;
      case USER:
        return UserRole.USER;
      case HOST:
        return UserRole.HOST;
      case UNRECOGNIZED:
      default:
        throw new IllegalArgumentException("Unknown role: " + protoRole);
    }
  }

  public UserStatus mapProtoStatusToJava(Status protoStatus) {
    switch (protoStatus) {
      case ACTIVE:
        return UserStatus.ACTIVE;
      case PENDING:
        return UserStatus.PENDING;
      case INACTIVE:
        return UserStatus.INACTIVE;
      case UNRECOGNIZED:
      default:
        throw new IllegalArgumentException("Unknown role: " + protoStatus);
    }
  }

}
