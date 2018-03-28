#!/usr/bin/env bash

cd weather-playlist-api;	./gradlew build;	cd -
docker-compose build
docker-compose up
