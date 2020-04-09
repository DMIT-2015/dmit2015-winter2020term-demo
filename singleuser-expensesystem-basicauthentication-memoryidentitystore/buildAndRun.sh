#!/bin/sh
mvn clean package && docker build -t dmit2015/singleuser-expensesystem-basicauthentication-memoryidentitystore .
docker rm -f singleuser-expensesystem-basicauthentication-memoryidentitystore || true && docker run -d -p 9080:9080 -p 9443:9443 --name singleuser-expensesystem-basicauthentication-memoryidentitystore dmit2015/singleuser-expensesystem-basicauthentication-memoryidentitystore