package se.icus.mag.modsettings.gui;

import net.minecraft.client.gui.screen.Screen;

public record ModSettingsOption(String modId, String modName, Screen configScreen) {
}
