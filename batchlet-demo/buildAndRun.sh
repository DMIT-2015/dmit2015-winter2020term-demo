#!/bin/sh
mvn clean package && docker build -t dmit2015/batchletd-demo .
docker rm -f batchletd-demo || true && docker run -d -p 9080:9080 -p 9443:9443 --name batchletd-demo dmit2015/batchletd-demo