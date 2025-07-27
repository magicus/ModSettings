# Mod Settings

[![Modrinth](https://img.shields.io/modrinth/dt/mod-settings?logo=modrinth)](https://modrinth.com/mod/mod-settings)
[![CurseForge](https://img.shields.io/curseforge/dt/544608?logo=curseforge)](https://www.curseforge.com/minecraft/mc-mods/mod-settings)
[![GitHub](https://img.shields.io/github/downloads/magicus/ModSettings/total?logo=github)](https://github.com/magicus/ModSettings/releases)

This is a Minecraft mod for Fabric that provides an easy to access configuration
screen for all installed mods!

Similar to how "Mod Options" works in Forge, this will add a Vanilla-style menu
screen with easy-to-access buttons to get to the configurations for your mods.

You can go directly to this screen using the Mod Settings hotkey. This means
there is no need to memorize all those hotkeys for settings of all your mods.
The Mod Settings hotkey is `F6` by default, but this can be changed in the
ordinary "Key Binds" options screen.

I created this mod when I found myself over and over again go into the Mod Menu
list, scroll to the bottom to find Tweakeroo or whatever, and then try to hit
the configuration button on the opposite side of the screen. With this mod, you
can directly go to the configuration screen with just a single button, and
unless you have an extreme amount of mods installed, they are all likely to fit
on a single page.

This mod requires the Fabric loader and the Fabric API. See dependencies below.

## Screenshot

This is what it looks like when you are using the mod.

![Screenshot of Mod Options menu](screenshot-1.png?raw=true)

![Screenshot of in-game menu](screenshot-2.png?raw=true)

## Interoperability

Mod Settings work fine with Mod Menu. It does not require the Mod Menu mod to be
present, but if it is, it tries to add the "Mod Settings..." button close to the
"Mods" button from Mod Menu.

Mod Settings use the API created by Mod Menu to query the mods for how to
present a configuration screen. All mods that can be configured from within Mod
Menu will show up on the Mod Settings screen, and conversely, mods that cannot
be configured from within Mod Menu will not show up on the Mod Settings screen.

## Dependencies

This mod needs Fabric API. If you have a modded Minecraft, chances are it is
already installed. Otherwise, you can find it here: [Fabric API on Modrinth](
https://modrinth.com/mod/fabric-api).

## Download

You can download the mod from any of these sites:

* [GitHub releases](https://github.com/magicus/ModSettings/releases)
* [Modrinth versions](https://modrinth.com/mod/mod-settings/versions)
* [CurseForge](https://www.curseforge.com/minecraft/mc-mods/mod-settings/files)

## Installation

Install this as you would any other Fabric mod. (I recommend using a replacement launcher such as Prism Launcher for modded Minecraft.)

## Support

Do you have any problems with the mod? Please open an issue here on Github.

## Known Incompatibilities

The mod [Content Creator Integration](
https://www.curseforge.com/minecraft/mc-mods/content-creator-integration)
versions 1.9.3 and older contains an incompatible implementation of the Mod Menu
API. This is fixed in newer versions of CCI.
