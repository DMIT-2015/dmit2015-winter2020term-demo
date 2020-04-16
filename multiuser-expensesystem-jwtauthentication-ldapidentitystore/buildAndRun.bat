@echo off
call mvn clean package
call docker build -t dmit2015/multiuser-expensesystem-jwtauthentication-ldapidentitystore .
call docker rm -f multiuser-expensesystem-jwtauthentication-ldapidentitystore
call docker run -d -p 9080:9080 -p 9443:9443 --name multiuser-expensesystem-jwtauthentication-ldapidentitystore dmit2015/multiuser-expensesystem-jwtauthentication-ldapidentitystore