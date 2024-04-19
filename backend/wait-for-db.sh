#!/bin/sh

set -e

host="$1"
port="$2"
shift 2

until nc -z "$host" "$port"; do
  echo "Waiting for the database to be ready..."
  sleep 1
done

echo "Database is ready. Starting the backend..."

exec "$@"