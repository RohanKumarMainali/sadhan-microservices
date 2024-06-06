#!/bin/bash

# Remove old keys and certificates
rm -f *.pem

# Generate CA's private key
openssl genrsa -des3 -out ca.key.pem 2048

# Create CA's self-signed certificate
openssl req -x509 -new -nodes -key ca.key.pem -sha256 -days 365 -out ca.cert.pem -config ca.cnf

# Create server private key
openssl genrsa -out localhost.key 2048

# Create server CSR with SANs
openssl req -new -key localhost.key -out localhost.csr -config san.cnf

# Sign server CSR with CA certificate
openssl x509 -req -in localhost.csr -CA ca.cert.pem -CAkey ca.key.pem -CAcreateserial -out localhost.crt -days 365 -extfile san.cnf -extensions v3_req

# Convert server private key to PKCS8 format
openssl pkcs8 -topk8 -nocrypt -in localhost.key -out localhost.pem

# Verification
echo "Verify CA certificate"
openssl x509 -noout -text -in ca.cert.pem | grep -A 1 "Basic Constraints"

echo "Verify server certificate"
openssl x509 -noout -text -in localhost.crt | grep -A 1 "Subject Alternative Name"
