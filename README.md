# Odyssée Chargée

**Javadoc:** [xajason.github.io/odyssee-chargee](https://xajason.github.io/odyssee-chargee/)

Odyssée Chargée is a 2D physics-based game built in pure Java Swing, with rigid-body simulation, electromagnetic plate mechanics, portal teleportation, and a built-in level editor.

Originally developed as a 4-person capstone project at Collège de Maisonneuve, this repository contains the maintained version of the project.

## Overview

The game challenges players to navigate a ship through levels shaped by gravity, friction, electric forces, portals, and hazards. It was built without an external game engine, so the rendering, physics, collision handling, and editor systems were all implemented directly in Java.

It includes a custom level editor, 6 campaign levels, save and load support, background music, and hosted Javadoc documentation.

## What I built

My main work focused on the level editor, tile architecture, and much of the UI.

- Led development of the built-in level editor used to create and modify custom levels.
- Implemented tile rotation and save support for custom map creation.
- Designed an inheritance-based tile system around a shared `Tuile` base class.
- Used shared abstractions such as `Dessinable`, `Serializable`, and `Selectionnable` to keep display, persistence, and selection behavior consistent across tile types.
- Helped structure the tile system so teammates could work on different tile types and features in parallel.
- Contributed to most of the UI panels used across the game and editor.

## Features

- Real-time 2D physics simulation in Java.
- Electromagnetic plates that push or pull the ship.
- Portal teleportation and hazard tiles.
- Keyboard-controlled ship movement with jetpack thrust.
- Built-in level editor with tile placement and rotation.
- Save and load support for custom levels.
- 6 bundled campaign levels.
- Background music, settings, and hosted Javadoc.

## Tech stack

- Java 17
- Java Swing and AWT
- Java Object Serialization
- `java.nio.file` for cross-platform file handling
- `javax.sound.sampled` for audio
- GitHub Actions for Javadoc publishing

## Architecture

The project is split across UI, game logic, physics, math, and tile systems. Core components include the game panels, tile grid, tile hierarchy, physics engine, collision handling, force calculations, and supporting math utilities.

The tile system is inheritance-based, with specialized tile classes built on a shared base class. This made the editor easier to extend and helped keep tile behavior consistent across rendering, saving, and user interaction.

## Preview

![Gameplay Demo](demo/gameplay.gif)

![Level Editor](demo/editor.gif)

![Electromagnetic Plates](demo/physics.gif)

## Run the project

### Option 1: Runnable JAR

1. Download `odyssee-chargee.jar` from the [Releases](../../releases) tab.
2. Make sure Java 17 or newer is installed.
3. Place the JAR next to the `ressources/` directory.
4. Run:

```bash
java -jar odyssee-chargee.jar
```

### Option 2: Eclipse

1. Clone the repository:

```bash
git clone https://github.com/XaJason/odyssee-chargee.git
```

2. Import the project into Eclipse.
3. Run `application.Main` as a Java application.

### Option 3: Command line

```bash
git clone https://github.com/XaJason/odyssee-chargee.git
cd odyssee-chargee
javac -encoding UTF-8 -d bin $(find src -name "*.java")
java -cp bin application.Main
```

## Project structure

```text
odyssee-chargee/
├── src/
├── ressources/
├── demo/
├── doc/
├── .github/workflows/
└── README.md
```

## Team

Originally developed as a capstone project by a 4-person team at Collège de Maisonneuve.

- Jason Xa: level editor, tile system, UI panels
- Kitimir Yim: application lifecycle, level serialization, audio
- Enuel Rene Valentin Kizozo Izia: physics engine, collision detection, electromagnetic force model
- Pierre-Olivier Giroux: tile geometry, portal logic, grid management

## Notes

If the project were extended further, the next steps would likely be automated tests for the physics and serialization systems, more UI polish, and performance improvements for large levels.

## License

This project was developed for academic purposes. All rights reserved, Equipe 22, 2024.