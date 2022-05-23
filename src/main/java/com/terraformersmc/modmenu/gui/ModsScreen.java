package com.terraformersmc.modmenu.gui;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

/**
 * Ideally, this class should not really have been needed, but some mods (e.g. older versions of
 * Content Creator Integration mod) apparently consider this class part of the ModMenu API, and
 * uses it for instanceof checks. That will cause exceptions if Mod Menu is not also installed.
 */
public class ModsScreen extends Screen {
  protected ModsScreen(Text title) {
    super(title);
  }
}
