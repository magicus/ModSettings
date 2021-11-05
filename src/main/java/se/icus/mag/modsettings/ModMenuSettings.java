package se.icus.mag.modsettings;

import net.fabricmc.api.ClientModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ModSettings implements ClientModInitializer {
    public static final Logger LOGGER = LogManager.getLogger("modsettings");

    @Override
    public void onInitializeClient() {
        ModRegistry.registerMods();
    }

}
