#!/usr/bin/env bash

cd weather-playlist-api;	./gradlew clean build;	cd -
docker-compose build
docker-compose up
