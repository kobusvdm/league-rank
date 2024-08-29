#!/bin/sh

if [ -z "${_SRC_DIR}" ]; then
    _SRC_DIR=$(realpath $(pwd))
fi

_pushd () {
    pushd "$@" > /dev/null
}

_popd () {
    popd "$@" > /dev/null
}

# Variables
# Base vars
_TMP_DIR="${_SRC_DIR}/.temp"
_BIN_DIR="${_TMP_DIR}/bin"
_STATE_DIR="${_TMP_DIR}/state"

case ":$_BIN_DIR:" in
    *":${PATH}:"*) ;;
    *) export PATH="${_BIN_DIR}:${PATH}" ;;
esac

# End vars

export _pushd _popd

#Variable exports
export _SRC_DIR _TMP_DIR _BIN_DIR _STATE_DIR

    