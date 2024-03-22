echo "> Checking running Spring Boot application"
CURRENT_PID=$(sudo lsof -ti tcp:8080)

if [ -n "$CURRENT_PID" ]; then
    echo "> current springboot (PID: $CURRENT_PID)"
    sudo kill -9 "$CURRENT_PID"
    sleep 5
fi


echo "> Deploying new Spring Boot application"

cd /home/ubuntu/webduck/backend/

sed -i 's/active: test/active: prod/' ./src/main/resources/application.yml

./gradlew clean build

cd build/libs/

sudo nohup java -jar WebDuck-0.0.1-SNAPSHOT.jar > /home/ubuntu/webduck/backend/build/libs/nohup.out 2>&1 &

echo "> Deployment completed"