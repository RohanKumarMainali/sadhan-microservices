
�
schema.protocom.sadhan.proto">

JwtRequest
email (	Remail
password (	Rpassword"y
JwtResponse

statusCode (R
statusCode
jwtToken (	RjwtToken
error (	Rerror
message (	Rmessage"z
Employee
id (	Rid
name (	Rname
email (	Remail
phone (	Rphone

department (	R
department"�
User
name (	Rname
email (	Remail
password (	Rpassword*
role (2.com.sadhan.proto.RoleRrole0
status (2.com.sadhan.proto.StatusRstatus"Y
CreateUserRequest
name (	Rname
email (	Remail
password (	Rpassword"d
CreateUserResponse

statusCode (R
statusCode
error (	Rerror
message (	Rmessage*%
Role	
ADMIN 
USER
HOST*/
Status

ACTIVE 
INACTIVE
PENDING2\
AuthServiceM
authenticate.com.sadhan.proto.JwtRequest.com.sadhan.proto.JwtResponse" 2h
UserServiceY

createUser#.com.sadhan.proto.CreateUserRequest$.com.sadhan.proto.CreateUserResponse" BPbproto3
