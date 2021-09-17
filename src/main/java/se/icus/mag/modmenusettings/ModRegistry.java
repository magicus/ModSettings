package se.icus.mag.modmenusettings;

import com.google.common.collect.ImmutableMap;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import com.terraformersmc.modmenu.util.ModMenuApiMarker;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.metadata.ModMetadata;
import net.minecraft.client.gui.screen.Screen;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.function.Supplier;

public class ModRegistry {
    private static List<Supplier<Map<String, ConfigScreenFactory<?>>>> dynamicScreenFactories = new ArrayList<>();
    private static ImmutableMap<String, ConfigScreenFactory<?>> configScreenFactories = ImmutableMap.of();
    public static final Logger LOGGER = LogManager.getLogger("Mod Menu");
    public static final Map<String, String> CONFIGABLE_MODS = new HashMap<>();

    public static void registerMods() {
        Map<String, ConfigScreenFactory<?>> factories = new HashMap<>();
        FabricLoader.getInstance().getEntrypointContainers("modmenu", ModMenuApiMarker.class).forEach(entrypoint -> {
            ModMetadata metadata = entrypoint.getProvider().getMetadata();
            String modId = metadata.getId();
            String modName = metadata.getName();
            CONFIGABLE_MODS.put(modId, modName);
            try {
                ModMenuApiMarker marker = entrypoint.getEntrypoint();
                if (marker instanceof ModMenuApi) {
                    /* Current API */
                    ModMenuApi api = (ModMenuApi) marker;
                    factories.put(modId, api.getModConfigScreenFactory());
                    dynamicScreenFactories.add(api::getProvidedConfigScreenFactories);
                } else if (marker instanceof io.github.prospector.modmenu.api.ModMenuApi) {
                    /* Legacy API */
                    io.github.prospector.modmenu.api.ModMenuApi api = (io.github.prospector.modmenu.api.ModMenuApi) entrypoint.getEntrypoint();
                    factories.put(modId, screen -> api.getModConfigScreenFactory().create(screen));
                    api.getProvidedConfigScreenFactories().forEach((id, legacyFactory) -> factories.put(id, legacyFactory::create));
                } else {
                    throw new RuntimeException(modId + " is providing an invalid ModMenuApi implementation");
                }
            } catch (Throwable e) {
                LOGGER.error("Mod {} provides a broken implementation of ModMenuApi", modId, e);
            }
        });
        configScreenFactories = ImmutableMap.copyOf(factories);

    }

    public static Screen getConfigScreen(String modid, Screen menuScreen) {
        ConfigScreenFactory<?> factory = configScreenFactories.get(modid);
        if (factory != null) {
            return factory.create(menuScreen);
        }
        for (Supplier<Map<String, ConfigScreenFactory<?>>> dynamicFactoriesSupplier : dynamicScreenFactories) {
            factory = dynamicFactoriesSupplier.get().get(modid);
            if (factory != null) {
                return factory.create(menuScreen);
            }
        }
        return null;
    }
}
