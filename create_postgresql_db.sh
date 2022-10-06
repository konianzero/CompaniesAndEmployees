#!/bin/bash

# Stops the execution of a script if a command or pipeline has an error
set -e

# Default params
DB_NAME=${1:-info}
DB_USER=${2:-user}
DB_USER_PASS=${3:-password}

sudo su postgres <<EOF
createdb  $DB_NAME;
psql -c "CREATE USER $DB_USER WITH PASSWORD '$DB_USER_PASS';"
psql -c "grant all privileges on database $DB_NAME to $DB_USER;"
echo "Postgres User '$DB_USER' and database '$DB_NAME' created."
EOF