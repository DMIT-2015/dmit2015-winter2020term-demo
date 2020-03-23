@echo off
call mvn clean package
call docker build -t northwind/northwind-demo .
call docker rm -f northwind-demo
call docker run -d -p 9080:9080 -p 9443:9443 --name northwind-demo northwind/northwind-demo