#!/bin/bash

if [ "$1" == "web" ]; then
  export WEB_PORT="5050"
  export EXPENSES_SERVER="http://localhost:8001"
  export CLAIMS_SERVER="http://localhost:8002"
  export RATINGS_SERVER="http://localhost:8003"
  echo "web environment set up successfully, arg: $1"
elif [ "$1" == "expenses" ]; then
  export EXPENSE_PORT="8001"
  echo "expenses environment set up successfully, arg: $1"
elif [ "$1" == "claims" ]; then
  export CLAIMS_PORT="8002"
  export EXPENSES_SERVER=""
  echo "claims environment set up successfully, arg: $1"
elif [ "$1" == "ratings" ]; then
  export RATINGS_PORT="8003"
  echo "ratings environment set up successfully, arg: $1"
else
  echo "Invalid arg for environment setup: $1"
fi