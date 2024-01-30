#!/bin/bash

SCRIPT_DIRECTORY=$( cd -- "$( dirname -- "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )
PROJECT_DIRECTORY=$(dirname "${SCRIPT_DIRECTORY}")
PROJECT_NAME=$(basename "${PROJECT_DIRECTORY}")
TARGET_DIRECTORY="${PROJECT_DIRECTORY}/target"

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

function kill_process() {
  local kill_pid="$1"
  kill -9 "$kill_pid"
}

cd "${TARGET_DIRECTORY}" || location_exit_1 "${TARGET_DIRECTORY}"

JAR_FILE_NAME="$(find . -name '*.jar')"
if [ -z "$JAR_FILE_NAME" ]; then
  JAR_FILE_NAME="./${PROJECT_NAME}"
fi

PID=($(ps -ax | grep "${JAR_FILE_NAME}" | grep -v 'grep' | awk '{print $1}'))
for single_pid in "${PID[@]}"; do
  kill_process "$single_pid"
done

cd "${PROJECT_DIRECTORY}" || location_exit_1 "${PROJECT_DIRECTORY}"
mvn clean