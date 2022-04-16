package se.icus.mag.modsettings;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.glfw.GLFW;
import se.icus.mag.modsettings.gui.ModSettingsScreen;

public class Main implements ClientModInitializer {
    public static final Logger LOGGER = LogManager.getLogger("modsettings");

    @Override
    public void onInitializeClient() {
        ModRegistry.getInstance().registerMods();

        KeyBinding modSettingsKey = KeyBindingHelper.registerKeyBinding(new KeyBinding("modsettings.key.open", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_F6, "modsettings.key.category"));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (modSettingsKey.wasPressed()) {
                client.getInstance().setScreen(new ModSettingsScreen(null));
            }
        });
    }
}
