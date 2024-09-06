#!/bin/bash

# Очищення старих збірок і створення нової
./gradlew clean build

# Перехід до директорії з Dockerfile
cd ./docker

# Запуск Docker Compose з передачею змінних середовища
DB_USERNAME=myuser DB_PASSWORD=mypassword docker-compose up -d --build


