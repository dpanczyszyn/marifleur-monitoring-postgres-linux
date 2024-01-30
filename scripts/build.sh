#!/bin/bash

SCRIPT_DIRECTORY=$( cd -- "$( dirname -- "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )
PROJECT_DIRECTORY=$(dirname "${SCRIPT_DIRECTORY}")

function location_exit_1() {
  local project_directory="$1"
  echo "error: project directory does not exist at: ${project_directory}"
  exit 1
}

cd "$PROJECT_DIRECTORY" || location_exit_1 "$PROJECT_DIRECTORY"
mvn clean package -DskipTests