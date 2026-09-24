# Astral Core

The **magic-systems library** of the [Majestic](https://gitlab.com/stalking-dragons/minecraft/majestic)
mod ecosystem for Minecraft NeoForge. Astral Core provides the frameworks; it ships **no concrete
content** of its own. Consumer mods and datapacks build on its API.

## What it provides

- **Essence** — a per-player magic resource: capacity, regeneration, stacking modifiers (altar
  proximity, biome, relics, research). Server-authoritative, synced to clients for HUD/UI.
- **Casting** — a spell/ability registry (`SpellType`), cast context, targeting helpers,
  per-player cooldowns, and client-side particle/sound/animation hooks.
- **Rituals** — a multiblock altar engine: multiblock definition and scan, `RitualType`, phases,
  and configurable failure risk. Relic forging is a high-tier ritual, not a separate recipe type.
- **Research** — a knowledge graph of constellation nodes with per-player unlock state and
  triggers ("on item obtained", "on structure entered", …).
- **Relics** — a relic behaviour contract (passive/active), a bridge to
  [Regalia Slots API](https://gitlab.com/stalking-dragons/minecraft/regalia-slots-api) for
  equip slots, and set-synergy support.

## Requirements

- Minecraft 1.21.1
- NeoForge 21.1.249
- Java 21

## Dependencies

- [Regalia Slots API](https://gitlab.com/stalking-dragons/minecraft/regalia-slots-api) (LGPL-3.0) — equip slots for relics.

Regalia Slots API is an **external dependency**: installed as a separate jar, never bundled.
[Common Toolkit](https://gitlab.com/stalking-dragons/minecraft/common-toolkit) (MIT) is planned but is not a dependency yet.

## Building from Source

```bash
./gradlew build
```

The built JAR will be in `build/libs/`.

## License

MIT — see [LICENSE](LICENSE).
