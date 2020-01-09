#!/bin/sh
mvn clean package && docker build -t ca.nait.dmit/dmit2015-demos .
docker rm -f dmit2015-demos || true && docker run -d -p 9080:9080 -p 9443:9443 --name dmit2015-demos ca.nait.dmit/dmit2015-demos