#!/bin/bash

echo "> Starting health check"

for i in {1..10}; do
    HEALTH=$(curl -s http://localhost:8080/api/v1/health)
    if [ "$HEALTH" == "success" ]; then
        echo "> Health check success!!"
        exit 0
    else
        echo "> Health check attempt $i failed...."
        sleep 10
    fi
done

echo "> Health check failed"
exit 1
