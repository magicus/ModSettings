/*
 * Copyright © Magnus Ihse Bursie 2025.
 * This file is released under the MIT License. See LICENSE for full license details.
 */
package se.icus.mag.modsettings;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.glfw.GLFW;
import se.icus.mag.modsettings.gui.screen.ModSettingsScreen;

public class Main implements ClientModInitializer {
    public static final Logger LOGGER = LogManager.getLogger("modsettings");
    public static final Options OPTIONS = new Options();

    public static KeyBinding modSettingsKey;

    @Override
    public void onInitializeClient() {
        ModRegistry.getInstance().registerMods();

        modSettingsKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "modsettings.key.open", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_F6, "modsettings.key.category"));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.currentScreen == null) {
                checkForModKeyPress(client, null);
            }
        });

        ScreenEvents.AFTER_INIT.register((client, screen, scaledWidth, scaledHeight) -> {
            if (!(screen instanceof ModSettingsScreen)) {
                ScreenEvents.afterTick(screen).register((Screen s) -> {
                    checkForModKeyPress(client, s);
                });
            }
        });
    }

    private static void checkForModKeyPress(MinecraftClient client, Screen screen) {
        InputUtil.Key boundKey = KeyBindingHelper.getBoundKeyOf(modSettingsKey);
        if (InputUtil.isKeyPressed(client.getWindow().getHandle(), boundKey.getCode())) {
            client.setScreen(new ModSettingsScreen(screen));
        }
    }

    public static class Options {
        public String filterText = "";
        public boolean showAllMods = false;
    }
}
