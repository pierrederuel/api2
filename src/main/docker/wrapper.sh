#!/bin/bash

##while ! exec 6<>/dev/tcp/${DATABASE_HOST}/${DATABASE_PORT}; do
    echo "Trying to connect to MySQL at ${DATABASE_PORT}..."
    sleep 60
##done

java -Djava.security.egd=file:/dev/./urandom -Dspring.profiles.active=container -jar /app.jar