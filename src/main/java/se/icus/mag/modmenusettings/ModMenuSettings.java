package se.icus.mag.modmenusettings;

import net.fabricmc.api.ClientModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ModMenuSettings implements ClientModInitializer {
    public static final String MOD_ID = "modmenusettings";
    public static final String MOD_NAME = "ModMenuSettings";

    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

    @Override
    public void onInitializeClient() {
        ModRegistry.registerMods();
    }

}
