#!/bin/bash

# If the launcher doesn't start, uncomment these two lines and look in
# "/tmp/run" after attempting to execute it to see debugging messages.
#exec >/tmp/run 2>&1
#set -x

DIR=$(dirname "$0")
cd "$DIR"
exec "java" \
    -Xdock:name="MapTool Launcher" \
    -Xdock:icon=../Resources/maptool-icon.icns \
    -Dapple.awt.showGrowBox=false \
    -splash:../Resources/launcher_splash.png \
    -cp "@LAUNCHER@" \
    net.rptools.maptool.launcher.MapToolLauncher \
    "$@" \
    2>&1
