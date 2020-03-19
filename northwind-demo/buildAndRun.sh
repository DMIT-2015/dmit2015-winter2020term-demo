#!/bin/sh
mvn clean package && docker build -t northwind/northwind-demo .
docker rm -f northwind-demo || true && docker run -d -p 9080:9080 -p 9443:9443 --name northwind-demo northwind/northwind-demo