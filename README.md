# Dungeon Crawler

This project is a functioning prototype of a procedurally generated, turn-based, and top-down dungeon crawler game. All code and other assets are free and open source under the MIT License (see [LICENSE.txt](https://github.com/xbru1/cs141-final/blob/main/LICENSE.txt)).

# Building/Running

## Prequisites
- Java 25
- Maven (if building from source)

## Running
Using [pre-built .jar from Releases](https://github.com/xbru1/cs141-final/releases/tag/1.0.0): `java -jar "crawler.jar"`

## Building from Source
```
git clone https://github.com/xbru1/cs141-final/
cd cs141-final
mvn package
```
The built .jar will be placed in `./target`.<br>
You can also run `mvn clean javafx:run` to test directly from source.

# Program Features
- Basic graphics using JavaFX
- Simple HUD
- Keyboard controls
- Procedurally generated levels containing enemies and items
- Turn-based movement/combat system
- Enemies with basic AI
- Item pickups
- Simple experience/leveling system
- Basic saving/loading system for player data

# How To Play
- You, the player, are the blue dot in the center of the screen. Your goal is simply to survive through as many floors as possible.
- Each floor of the dungeon contains enemies and items.
- Red dots are enemies that will attempt to chase and attack you. Move directly onto them to attack. Defeating them grants you experience that lets you level up your stats.
- Yellow dots are items. Move onto them to fully heal and gain some additional experience.
- There is a purple portal tile on each floor. Move into it to travel to the next one.

# Controls
- WASD/Arrow Keys: Move
- Space: Pass turn without moving
- Enter: Start a new game (only after dying)

# Notable External Resources Used
- [Maven Assembly Plugin](https://maven.apache.org/plugins/maven-assembly-plugin/): to make building to a .jar easier

# Final Note
Only the commit history starting from 6/13/2026 is present. Before that date, I had already been working on this in an entirely local Git repository. The metadata of that repository got corrupted, causing the commit history before that date to be lost. All code remained intact though, so no other work was lost. 
