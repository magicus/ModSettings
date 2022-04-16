package com.terraformersmc.modmenu.gui;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

/**
 * This class should not really have been needed, but some mods (e.g. the Content Creator
 * Integration mod) apparently consider this class part of the ModMenu API, and uses it for
 * instanceof checks. If the parent screen does not implement this class, CCI tries to resize the
 * window, which triggers a call to Screen.init(), which in turns calls ModSettingsScreen.init(),
 * which recurses until stack overflow.
 */
public class ModsScreen extends Screen {
  protected ModsScreen(Text title) {
    super(title);
  }
}
