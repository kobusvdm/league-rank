#!/bin/sh

. $_SRC_DIR/dev-env/99.env.sh
  
mkdir -p $_TMP_DIR/bin

if ! command -v cs &> /dev/null; then
  $_SRC_DIR/dev-env/99.install-cs.sh
fi

if ! command -v mill &> /dev/null; then
  $_SRC_DIR/dev-env/99.install-mill.sh
fi

if ! command -v g8 &> /dev/null; then
  $_SRC_DIR/dev-env/99.install-g8.sh
fi
