@echo off
call mvn clean package
call docker build -t dmit2015/javamail-timers-demo .
call docker rm -f javamail-timers-demo
call docker run -d -p 9080:9080 -p 9443:9443 --name javamail-timers-demo dmit2015/javamail-timers-demo