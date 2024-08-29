#!/bin/sh

. $_SRC_DIR/dev-env/99.env.sh

#0
_pushd $_TMP_DIR/bin  #1

./cs install mill

_popd #0
