package se.icus.mag.modsettings;

import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.resources.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.glfw.GLFW;
import se.icus.mag.modsettings.gui.ModSettingsScreen;

public class Main implements ClientModInitializer {
    public static final Logger LOGGER = LogManager.getLogger("modsettings");

    @Override
    public void onInitializeClient() {
        ModRegistry.getInstance().registerMods();

        KeyMapping.Category category = KeyMapping.Category.register(Identifier.fromNamespaceAndPath("modsettings", "main"));
        KeyMapping modSettingsKey = KeyMappingHelper.registerKeyMapping(new KeyMapping("modsettings.key.open", InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_F6, category));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (modSettingsKey.consumeClick()) {
                client.getInstance().gui.setScreen(new ModSettingsScreen(null));
            }
        });
    }
}
