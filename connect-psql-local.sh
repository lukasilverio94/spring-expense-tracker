#!/bin/bash

# Database connection variables
HOST="localhost"
PORT="5432"
USER="postgres"

echo "Connecting to PostgreSQL on $HOST:$PORT as user $USER..."
psql -h "$HOST" -p "$PORT" -U "$USER"
