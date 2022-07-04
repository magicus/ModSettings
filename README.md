# Mod Settings for Fabric

Provides an easy to access configuration screen for all installed mods!

Similar to how "Mod Options" worked in Forge, this will add a Vanilla-style menu
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

This mod requires Minecraft 1.17.1 - 1.19 and the Fabric loader.

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

The latest version is 1.1.0.

Direct download links for Minecraft 1.19:

* Download from GitHub: [modsettings-1.1.0+1.19.jar](
https://github.com/magicus/ModSettings/releases/download/v1.1.0%2B1.19/modsettings-1.1.0+1.19.jar)
* Download from Modrinth: [modsettings-1.1.0+1.19.jar](
https://cdn.modrinth.com/data/mfDfQvcJ/versions/1.1.0+1.19/modsettings-1.1.0%2B1.19.jar)
* Download from CurseForge: [modsettings-1.1.0+1.19.jar](
https://www.curseforge.com/minecraft/mc-mods/mod-settings/download/3858420)

For all other Minecraft releases, check these download sites:
* [GitHub releases](https://github.com/magicus/ModSettings/releases)
* [Modrinth versions](https://modrinth.com/mod/mod-settings/versions)
* [CurseForge](https://www.curseforge.com/minecraft/mc-mods/mod-settings/files)

## Installation

Install this as you would any other Fabric mod. (Personally, I recommend MultiMC
as Minecraft launcher for modded Minecraft.) [I still need help installing this
mod.](https://lmgtfy.app/?q=how+to+install+minecraft+fabric+mods)

## Support

Do you have any problems with the mod? Please open an issue here on Github.

## Known Incompatibilities

The mod [Content Creator Integration](
https://www.curseforge.com/minecraft/mc-mods/content-creator-integration)
versions 1.9.3 and older contains an incompatible implementation of the Mod Menu
API. The result is that the GUI scale is changed when opening Mod Settings, even
if you do not click the CCI button. The mod hardcodes references to
implementation details of Mod Menu itself so the bug does not manifest using Mod
Menu. This [will be fixed](
https://github.com/iChun/ContentCreatorIntegration-IssuesAndDocumentation/issues/82)
in future versions of CCI.
