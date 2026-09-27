# Firework Table
A Minecraft 1.20.1 Architectury port of the original 1.12.2 Forge mod. It adds a block to simplify crafting firework stars with a GUI.

## Project layout

- `common/` contains the shared block, menu, screen, registration, assets, and data.
- `fabric/` contains Fabric metadata and entrypoints.
- `forge/` contains Forge metadata and the Forge entrypoint.

## Build

Minecraft 1.20.1 requires Java 17 or newer.

```sh
bash ./gradlew :common:compileJava :fabric:compileJava :forge:compileJava
```

The Forge JAR is also compatible with NeoForge 1.20.1, where NeoForge retained Forge compatibility. No separate NeoForge build is produced. ForgeGradle requires a locally installed JDK 17 for its Minecraft recompile step.
