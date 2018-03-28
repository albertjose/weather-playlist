#!/usr/bin/env bash

cd weather-playlist-api;	./gradlew build -Dspring.profiles.active=docker;	cd -
docker-compose build
docker-compose up
