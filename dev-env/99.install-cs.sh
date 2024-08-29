#!/bin/sh

. $_SRC_DIR/dev-env/99.env.sh

_CFG_GUESS="$($_SRC_DIR/dev-env/99.config.guess.sh)"

#0
_pushd $_TMP_DIR/bin  #1

case $_CFG_GUESS in
*pc-linux-gnu)
    echo "Linux detected: [$_CFG_GUESS]"
    curl -fL "https://github.com/coursier/launchers/raw/master/cs-`arch`-pc-linux.gz" | gzip -d > cs
    ;;
*apple-darwin*)
    echo "Darwin detected: [$_CFG_GUESS]"
    curl -fL "https://github.com/VirtusLab/coursier-m1/releases/latest/download/cs-`arch`-apple-darwin.gz" | gzip -d > cs
    ;;
esac

chmod +x cs
./cs setup

_popd #0
