#!/bin/sh

. $_SRC_DIR/dev-env/99.env.sh
  
if ! command -v cs &> /dev/null; then
  echo "Failed to install or find cs"
fi

if ! command -v mill &> /dev/null; then
  echo "Failed to install or find mill"
fi

if ! command -v g8 &> /dev/null; then
  echo "Failed to install or find giter8"
fi
