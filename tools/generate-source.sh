#!/bin/bash
TEMPLATE_FILE=$1
DATA_FILE=$2
OUT_PATH=$3

if [ "$OUT_PATH" == "" ]; then
  echo "Output path not specified"
  exit 1
fi

echo "Running template $TEMPLATE_FILE"
mustache "$DATA_FILE" "$TEMPLATE_FILE" > $OUT_PATH
