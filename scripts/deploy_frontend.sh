#!/bin/bash

echo "> Building Vue.js application"
cd /home/ubuntu/WebDuck/frontend/
npm install
npm run build

echo "> Deploying new frontend to Nginx"
sudo rm -rf /var/www/html/dist/*
sudo cp -r dist/* /var/www/html/dist/

echo "> Restarting Nginx"
sudo nginx -t
sudo systemctl restart nginx

echo "> Frontend deployment completed"
