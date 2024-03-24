#!/bin/bash


echo "> Checking running Spring Boot application"

CURRENT_PORT=$(cat /home/ubuntu/service_url.inc | grep -Po '[0-9]+' | tail -1)
TARGET_PORT=0

echo "> Current WAS port is :${CURRENT_PORT}."


if [ "${CURRENT_PORT}" -eq 8080 ]; then
  TARGET_PORT=8081
elif [ "${CURRENT_PORT}" -eq 8081 ]; then
  TARGET_PORT=8080
else
  echo "> No WAS is connected to nginx"
fi

  echo "> Change TARGET_PORT to ${TARGET_PORT}"


TARGET_PID=$(sudo lsof -Fp -i TCP:${TARGET_PORT} | grep -Po 'p[0-9]+' | grep -Po '[0-9]+')
echo "> Checking that the new port is already running....[port:${TARGET_PORT}] "

if [ ! -z "${TARGET_PID}" ]; then
  echo "> New port is already running!"
  echo "> Kill the WAS running at ${TARGET_PORT}. [PID : ${TARGET_PID}]"
  sudo sudo kill "${TARGET_PID}"
else
  echo "> The new port is not running!"
fi


cd /home/ubuntu/webduck/backend/

./gradlew clean build

cd build/libs/

sudo nohup java -jar -Dserver.port=${TARGET_PORT} -Dspring.profiles.active=prod WebDuck-0.0.1-SNAPSHOT.jar > /home/ubuntu/webduck/backend/build/libs/nohup.out 2>&1 &
echo "> WAS Deployment completed"
echo "> New WAS port is ${TARGET_PORT}"
exit 0