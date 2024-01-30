#!/bin/bash

SCRIPT_DIRECTORY=$( cd -- "$( dirname -- "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )
PROJECT_DIRECTORY=$(dirname "${SCRIPT_DIRECTORY}")
PROJECT_NAME=$(basename "${PROJECT_DIRECTORY}")
TARGET_DIRECTORY="${PROJECT_DIRECTORY}/target"
JAVA_ARGUMENTS="-Ddatabase-log-directory=${PROJECT_DIRECTORY}/target/postgres-logs"

function location_exit_1() {
  local target_directory="$1"
  echo "error: project directory does not exist at: ${target_directory}"
  exit 1
}

function jar_exit_1() {
  local target_directory="$1"
  echo "error: .jar file does not exist at: ${target_directory} make sure to use build.sh script first"
  exit 1
}

function sigint_term() {
  local pid="$1"
  local project_directory="$2"
  echo -e "\nclosing process with pid:${pid}..."
  kill -9 "${pid}"
  cd "${project_directory}" || location_exit_1 "${project_directory}"

  mvn clean
  exit
}

cd "${TARGET_DIRECTORY}" || location_exit_1 "${TARGET_DIRECTORY}"

JAR_FILE_NAME="$(find . -name '*.jar')"
if [ -z "$JAR_FILE_NAME" ]; then
  jar_exit_1 "${TARGET_DIRECTORY}"
fi

trap 'sigint_term "$JAVA_PID" "$PROJECT_DIRECTORY"' SIGINT

# CURRENTLY
echo 'password' | su -c "java ""${JAVA_ARGUMENTS}"" -jar ""${JAR_FILE_NAME}""" -m "postgres" &
JAVA_PID=$!
wait "$JAVA_PID"
