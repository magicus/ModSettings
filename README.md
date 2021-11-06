# Mod Settings for Fabric

Provides an easy to access configuration screen for all installed mods!

Similar to how "Mod Options" worked in Forge, this will add a Vanilla-style menu
screen with easy-to-access buttons to get to the configurations for your mods.

I created this mod when I found myself over and over again go into the Mod Menu
list, scroll to the bottom to find Tweakeroo or whatever, and then try to hit
the configuration button on the opposite side of the screen. With this mod, you
can directly go to the configuration screen with just a single button, and
unless you have an extreme amount of mods installed, they are all likely to fit
on a single page.

This mod requires Minecraft 1.17.1 and the Fabric loader.

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

## Download

You can download the latest version here:
[modsettings-1.0.0.jar](https://github.com/magicus/ModSettings/releases/download/1.0.0/modsettings-1.0.0.jar)

## Installation

Install this as you would any other Fabric mod. (Personally, I recommend MultiMC
as Minecraft launcher for modded Minecraft.) [I still need help installing this
mod](https://lmgtfy.app/?q=how+to+install+minecraft+fabric+mods)

## Support

Do you have any problems with the mod? Please open an issue here on Github.

Currently only Minecraft version 1.17.1 is supported, but it would probably be
trivial to add support for other versions. If you want support for another
version, please open an issue and state the requested version.
