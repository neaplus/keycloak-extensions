#!/bin/sh

mvn package
docker build . -t keycloak:9plus
