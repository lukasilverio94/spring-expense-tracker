#!/bin/bash

# Database connection variables
HOST="localhost"
PORT="5432"
USER="postgres"
DB="expensetracker"

echo "Connecting to PostgreSQL on $HOST:$PORT as user $USER to DB $DB..."
psql -h "$HOST" -p "$PORT" -U "$USER" -d "$DB"
