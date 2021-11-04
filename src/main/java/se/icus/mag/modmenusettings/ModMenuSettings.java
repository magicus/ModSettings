package se.icus.mag.modmenusettings;

import net.fabricmc.api.ClientModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ModMenuSettings implements ClientModInitializer {
    public static final Logger LOGGER = LogManager.getLogger("modmenusettings");

    @Override
    public void onInitializeClient() {
        ModRegistry.registerMods();
    }

}
