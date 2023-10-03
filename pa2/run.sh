#!/bin/bash

# Check for argument
if [ "$#" -ne 1 ]; then
  echo "Usage: $0 <path-to-file>"
  exit 1
fi

gradle build
./gradlew installDist
build/install/pa2/bin/pa2 $1