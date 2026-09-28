# Firework Table
A Minecraft 1.21.1 Architectury port of the original 1.12.2 Forge mod. It adds a block to simplify crafting firework stars with a GUI.

## Project layout

- `common/` contains the shared block, menu, screen, registration, assets, and data.
- `fabric/` contains Fabric metadata and entrypoints.
- `neoforge/` contains NeoForge metadata and the NeoForge entrypoint.

## Build

Minecraft 1.21.1 requires Java 21 or newer.

```sh
bash ./gradlew :common:compileJava :fabric:compileJava :neoforge:compileJava
```

The project produces separate Fabric and NeoForge JARs. NeoForge uses the 1.21.1 userdev toolchain and requires a locally installed JDK 21.
