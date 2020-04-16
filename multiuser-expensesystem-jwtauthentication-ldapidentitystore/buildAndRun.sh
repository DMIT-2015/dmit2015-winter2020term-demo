#!/bin/sh
mvn clean package && docker build -t dmit2015/multiuser-expensesystem-jwtauthentication-ldapidentitystore .
docker rm -f multiuser-expensesystem-jwtauthentication-ldapidentitystore || true && docker run -d -p 9080:9080 -p 9443:9443 --name multiuser-expensesystem-jwtauthentication-ldapidentitystore dmit2015/multiuser-expensesystem-jwtauthentication-ldapidentitystore