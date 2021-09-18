package se.icus.mag.modmenusettings;

import com.google.common.collect.ImmutableMap;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import net.fabricmc.loader.api.EntrypointException;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.entrypoint.EntrypointContainer;
import net.fabricmc.loader.api.metadata.ModMetadata;
import net.minecraft.client.gui.screen.Screen;
import org.apache.logging.log4j.Level;
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

        List<EntrypointContainer<ModMenuApi>> modList = FabricLoader.getInstance().getEntrypointContainers("modmenu", ModMenuApi.class);
        List<EntrypointContainer<io.github.prospector.modmenu.api.ModMenuApi>> mod2List = FabricLoader.getInstance().getEntrypointContainers("modmenu", io.github.prospector.modmenu.api.ModMenuApi.class);

        for (EntrypointContainer<ModMenuApi> entryPoint : modList) {
            ModMetadata metadata = entryPoint.getProvider().getMetadata();
            String modId = metadata.getId();
            String modName = metadata.getName();
            LOGGER.log(Level.INFO,"found mod1: " + modId);
            try {
                ModMenuApi marker = entryPoint.getEntrypoint();

                LOGGER.log(Level.INFO, "is new mod1: " + modId);
                CONFIGABLE_MODS.put(modId, modName);

                /* Current API */
                ModMenuApi api = (ModMenuApi) marker;
                factories.put(modId, api.getModConfigScreenFactory());
                dynamicScreenFactories.add(api::getProvidedConfigScreenFactories);
            } catch (EntrypointException e) {
                LOGGER.warn("problem with " + modId + e);
            }
        }

        for (EntrypointContainer<io.github.prospector.modmenu.api.ModMenuApi> entryPoint : mod2List) {
            ModMetadata metadata = entryPoint.getProvider().getMetadata();
            String modId = metadata.getId();
            String modName = metadata.getName();
            LOGGER.log(Level.INFO,"found mod2: " + modId);
            try {
                io.github.prospector.modmenu.api.ModMenuApi marker = entryPoint.getEntrypoint();
                LOGGER.log(Level.INFO,"is old mod2: " + modId);
                CONFIGABLE_MODS.put(modId, modName);

                /* Legacy API */
                io.github.prospector.modmenu.api.ModMenuApi api = (io.github.prospector.modmenu.api.ModMenuApi) entryPoint.getEntrypoint();
                factories.put(modId, screen -> api.getModConfigScreenFactory().create(screen));
                api.getProvidedConfigScreenFactories().forEach((id, legacyFactory) -> factories.put(id, legacyFactory::create));
            } catch (EntrypointException e) {
                LOGGER.warn("problem with " + modId + e);
            }
        }

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
