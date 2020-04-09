@echo off
call mvn clean package
call docker build -t dmit2015/singleuser-expensesystem-basicauthentication-memoryidentitystore .
call docker rm -f singleuser-expensesystem-basicauthentication-memoryidentitystore
call docker run -d -p 9080:9080 -p 9443:9443 --name singleuser-expensesystem-basicauthentication-memoryidentitystore dmit2015/singleuser-expensesystem-basicauthentication-memoryidentitystore