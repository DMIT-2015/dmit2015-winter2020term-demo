#!/bin/sh
mvn clean package && docker build -t dmit2015/jaxrs-crud-demo .
docker rm -f jaxrs-crud-demo || true && docker run -d -p 9080:9080 -p 9443:9443 --name jaxrs-crud-demo dmit2015/jaxrs-crud-demo