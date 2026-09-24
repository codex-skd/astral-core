# Changelog

All notable changes to this project will be documented in this file.

## [0.0.0-beta.2]

### Fixed
- CurseForge: `regalia-slots-api` is now declared as a required dependency, so the CurseForge
  app/launcher installs it automatically. beta.1 was uploaded without it, and the game failed to
  load with "Mod astral_core requires regalia_slots_api". No code changes.

## [0.0.0-beta.1]

First versioned build. **Minecraft 1.21.1 / NeoForge 21.1.249** (Java 21).

### Added
- Initial project setup: build (`net.neoforged.moddev` 2.0.142), Parchment `2024.11.17`, GitLab CI
  mirror pipeline, `maven-publish` for downstream consumption.
- **Essence** (`essence/`): a single global per-player magic resource. Data attachment
  (`EssenceData`), server-authoritative API (`EssenceApi`), passive regen out of combat,
  registrable `EssenceModifier`s, server→client sync (`EssenceSyncPayload`).
- **Casting** (`cast/`): `Spell` behaviour contract with a full pipeline (`execute()` = PreCast
  event → cost check → `cast()` → PostCast event), `SpellType` custom registry, `CastContext`/
  `CastResult` (sealed), targeting helpers (raycast, AoE sphere, self, projectile spawn info),
  per-player per-`SpellType` cooldown tracking (`CooldownTracker`), client VFX hook registry
  (`SpellClientBehavior`).
- **Rituals** (`ritual/`): `Multiblock` (code-defined shape, tag-matched), `RitualType` custom
  registry, `Ritual`/`RitualContext`/`RitualResult` (sealed)/`RitualPhase`/`RitualRisk`, abstract
  `AltarBlockEntity` (structure validation, start/tick/complete lifecycle, NBT persistence).
- **Research** (`research/`): per-player knowledge graph — `ResearchNode`/`ResearchGraph`,
  `ResearchData` attachment, `ResearchApi` (unlock with prerequisite checks), `ResearchTrigger`,
  server→client sync.
- **Relics** (`relic/`): `Relic` (reuses `cast`'s `CastContext`/`CastResult` for `activate()`),
  `RelicType` custom registry, `RelicSlots` (slot metadata — actual slot assignment is tag-driven
  via **Regalia Slots API**, not code), abstract `RelicItem` (`Item implements ICurioItem`,
  delegates to `Relic`), `RelicTickHandler` (safe dispatch), `relic/synergy/` (`RelicSet` +
  `RelicSynergyRegistry.getActiveSetBonuses`).
- Debug command `/astralcore status`.
- **Regalia Slots API** (LGPL-3.0) wired as a real dependency (external jar, never bundled).

### Notes
- Astral Core is a library: it ships no concrete content — the Majestic content mod and datapacks
  build on this API. See `docs/DESIGN_ASTRAL_CORE_1-21-1.md` and the Majestic ecosystem design.
- `common_toolkit` is not wired yet (no milestone has needed it so far); will be added if/when
  required, always as an external jar.
- Implemented across three milestones (M1 essence+casting, M2 ritual+research, M3 relics), each
  delegated to OpenCode and independently verified by Claude (clean build + dedicated-server boot).
  Several real bugs were found and fixed post-delegation along the way — see
  `docs/DESIGN_ASTRAL_CORE_1-21-1.md §6` (Historial) for the full account.
- Not yet verified with real gameplay (no concrete spell/ritual/relic exists yet to cast/run/equip
  — that's `majestic`'s job). Verified so far: compiles, boots cleanly, registries bind correctly.
