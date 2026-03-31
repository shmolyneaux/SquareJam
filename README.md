ABOUT
=====

This is an Android game created by Stephen Molyneaux between December 22 and 28, 2012. 
It is a clone of the iOS game Bloop by @rustym. It was made using libgdx and 
the Universal Tween Engine. Due to the short development cycle the source code is not
that representative of what I make. This is also the first game I've finished in Java.

Download `square-jam-android.apk` and install on an Android device to start playing.


HOW TO PLAY
===========

The game can be played many ways. The intended way to play is for each player, up to
4 players, to pick a color, press their own color while attempting to prevent other 
players for pressing theirs.

Players can decide on their own rules for how many hands they can use and how physical
the game can get (ex. no grabbing each other's hands, can use both hands but only one
can press at a time, etc).


BUILDING
========

Prerequisites
-------------

- Java Development Kit (JDK) 8 or later

Project Structure
-----------------

- `square-jam/` — Core game logic (shared across all platforms)
- `square-jam-desktop/` — Desktop launcher using LWJGL
- `square-jam-android/` — Android application
- `square-jam-html/` — GWT/web version

All library dependencies (libgdx, LWJGL, Universal Tween Engine) are included
as JARs in each module's `libs/` directory.

Building the Desktop Version
-----------------------------

From the repository root, compile the core module and desktop launcher:

    # Compile the core game library
    javac -encoding Cp1252 \
      -cp "square-jam/libs/gdx.jar:square-jam/libs/tween-engine-api.jar" \
      -d build/classes \
      square-jam/src/com/example/squarejam/*.java

    # Compile the desktop launcher
    javac -encoding Cp1252 \
      -cp "square-jam/libs/gdx.jar:square-jam/libs/tween-engine-api.jar:square-jam-desktop/libs/gdx-backend-lwjgl.jar:square-jam-desktop/libs/gdx-natives.jar:square-jam-desktop/libs/gdx-backend-lwjgl-natives.jar:build/classes" \
      -d build/classes \
      square-jam-desktop/src/com/example/squarejam/Main.java

Running the Desktop Version
----------------------------

After building, run from the repository root so the game can find its assets:

    java -cp "build/classes:square-jam/libs/gdx.jar:square-jam/libs/tween-engine-api.jar:square-jam-desktop/libs/gdx-backend-lwjgl.jar:square-jam-desktop/libs/gdx-natives.jar:square-jam-desktop/libs/gdx-backend-lwjgl-natives.jar" \
      com.example.squarejam.Main

**Notes:**

- The source files use Windows-1252 encoding, so the `-encoding Cp1252`
  flag is required when compiling with `javac`.
- On Windows, replace the classpath separator `:` with `;` in all commands
  above (e.g. `-cp "square-jam/libs/gdx.jar;square-jam/libs/tween-engine-api.jar"`).
