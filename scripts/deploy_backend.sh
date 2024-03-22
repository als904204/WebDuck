#!/bin/bash


echo "> Checking running Spring Boot application"

CURRENT_PORT=$(cat /home/ubuntu/service_url.inc | grep -Po '[0-9]+' | tail -1)
TARGET_PORT=0

echo "> Current port of running WAS is ${CURRENT_PORT}."


if [ "${CURRENT_PORT}" -eq 8080 ]; then
  TARGET_PORT=8081
elif [ "${CURRENT_PORT}" -eq 8081 ]; then
  TARGET_PORT=8080
else
  echo "> No WAS is connected to nginx"
fi

TARGET_PID=$(lsof -Fp -i TCP:${TARGET_PORT} | grep -Po 'p[0-9]+' | grep -Po '[0-9]+')

if [ ! -z "${TARGET_PID}" ]; then
  echo "> Kill WAS running at ${TARGET_PORT}."
  sudo kill "${TARGET_PID}"
fi


cd /home/ubuntu/webduck/backend/

./gradlew clean build

cd build/libs/

sudo nohup java -jar -Dspring.profiles.active=prod WebDuck-0.0.1-SNAPSHOT.jar > /home/ubuntu/webduck/backend/build/libs/nohup.out 2>&1 &

echo "> Deployment completed"
echo "> New WAS port is ${TARGET_PORT}"
exit 0