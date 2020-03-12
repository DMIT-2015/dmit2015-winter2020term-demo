#!/bin/sh
mvn clean package && docker build -t dmit2015/dmit2015-assignment04-related-demo .
docker rm -f dmit2015-assignment04-related-demo || true && docker run -d -p 9080:9080 -p 9443:9443 --name dmit2015-assignment04-related-demo dmit2015/dmit2015-assignment04-related-demo