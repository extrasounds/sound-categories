# Changelog

## [Unreleased]
### ⌛ Coming

<small>_Exciting new features may be coming here._</small>

### 💭 Bugs / Issues

<small>_Caught bugs and known issues are written._</small>

## [2.0.0+1.21.8-build.5] - 2025-08-17
### 🗘 Updated

* Show vanilla’s Music Frequency button, Music Toast button for MC 1.21.6 or later.

## [2.0.0+1.21.8-build.2] - 2025-07-24
### ✨ Added

* Supports 1.21.6 - 1.21.8.

### 👷 Technical

* Dependency updates:
  - yarn mappings 1.21.8+build.1
  - fabric api 0.129.0+1.21.8
  - fabric loader 0.16.14
  - fabric loom 1.11
  - Gradle 8.14.3

## [2.0.0+1.21.5-build.1] - 2025-04-11
### ✨ Added

* Supports 1.21.5.

### 👷 Technical

* Dependency updates:
  - yarn mappings 1.21.5+build.1
  - fabric api 0.119.5+1.21.5
  - fabric loader 0.16.10
  - fabric loom 1.10
  - Gradle 8.13

## [2.0.0+1.21.4-build.1] - 2024-12-14
### ✨ Added

* Supports 1.21.2 - 1.21.4.

### 👷 Technical

* Dependency updates:
  - yarn mappings 1.21.4+build.1
  - fabric api 0.110.5+1.21.1

## [2.0.0+1.21.1-build.1] - 2024-08-09
### ✨ Added

* Supports 1.21.1.

### 👷 Technical

* Dependency updates:
  - yarn mappings 1.21.1+build.1
  - fabric api 0.102.0+1.21.1

## [2.0.0+1.21-build.1] - 2024-06-14
### ✨ Added

* Bumped version to v2.
  - Compatible with Minecraft 1.18 or later.

### 👷 Technical

* Dependency updates:
  - yarn mappings 1.21-build.1
  - fabric api 0.100.1+1.21
  - fabric loader 0.15.11
  - Gradle 8.8

* Set Java language level to 17 to fit the minimum bytecode version
  supported by Minecraft’s JRE.
* Modularize packages to fit each Minecraft environment.
* Use much of reflections to execute code
  suitable for the current Minecraft runtime environment.

## [1.2.4+1.20.5-build.2] - 2024-05-14
### 🔧 Fixed

* Screen widgets now fit window dimension.

## [1.2.4+1.20.5-build.1] - 2024-04-24
### ✨ Added

* Port to 1.20.5.

### 👷 Technical

* Dependency updates:
  - Gradle 8.7
  - fabric-loom 1.6
  - Java 21
* Append Git commit hash to version.


## [1.2.4+1.20.4-build.1] - 2023-12-09
### ✨ Added

* Port to 1.20.4.

### 👷 Technical

* Dependency updates:
  - Gradle 8.4
  - fabric-loom 1.4

## [1.2.4+1.20.2-build.1] - 2023-09-22
### ✨ Added

* Port to 1.20.2.

### 🔧 Fixed

* Shrink `Done` button width to fit vanilla screen.

### 👷 Technical

* Dependency updates:
  - Gradle 8.3
  - fabric-loom 1.3

## [1.2.4+1.20-build.1] - 2023-06-08
### ✨ Added

* Port to 1.20.

### 👷 Technical

* Dependency updates:
  - Gradle 8.1.1
  - fabric-loom 1.2
  - fabric-loader 0.14.21
* Updates the API specification.
  - The "master" category now includes its class info and can be sorted by class name,
    and can only be specified once per class.
  - The enum `SoundCategory` generator now includes the modId prefix to reduce the
    possibility of name collisions. The field now contains its modId and `$`, such as
    `EXTRASOUNDS$MASTER`, and also localization keys need to be updated.

## [1.2.4+1.19.4-build.3] - 2023-05-21
### ✨ Added

* Annotation value `tooltip` to allow to display specified tooltip.

## [1.2.4+1.19.4-build.2] - 2023-03-27
### ✨ Added

* Annotation value `toggle` to allow to be displayed as a button widget.
* Displays in two columns on `SoundGroupOptionsScreen`.

## [1.2.4+1.19.4-build.1] - 2023-03-17
### ✨ Added

* Port to 1.19.4.

### 👷 Technical

* Dependency updates:
  - Java 17
  - Gradle 8.0.1
  - fabric-loom 1.1

## [1.2.4+1.19.3-SNAPSHOT]
### ✨ Added

* Port to 1.19.3.

## [1.2.4] - 2022-06-05

### Fixed

- Sound category volume not being saved

## [1.2.3] - 2022-06-05

### Fixed

- Sound categories being null when mods are being initialized
- Default float not being applied if settings are not yet saved

## [1.2.2] - 2022-06-01

### Changed

- Made default level mixin optional - this makes Optifine run with SoundCategories, however the default levels for all
  sounds will always be 100%.

## [1.2.1] - 2022-01-02

### Changed

- Added Optifine/Optifabric as an incompatible mod - causes crash on startup
- Added Fabric API as a required dependency

## [1.2.0] - 2021-12-28

### Added

- Category grouping via `master` attribute
- Configurable default volume levels

### Fixed

- Done button offset

## [1.1.0] - 2021-12-15

### Changed

- Replaced registration function with annotations

### Fixed

- Sound categories not being registered after init

## [1.0.0] - 2021-12-15

Initial release