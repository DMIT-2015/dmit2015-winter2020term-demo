@echo off
call mvn clean package
call docker build -t dmit2015/batchletd-demo .
call docker rm -f batchletd-demo
call docker run -d -p 9080:9080 -p 9443:9443 --name batchletd-demo dmit2015/batchletd-demo