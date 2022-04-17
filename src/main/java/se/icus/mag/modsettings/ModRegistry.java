package se.icus.mag.modsettings;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import net.fabricmc.loader.api.EntrypointException;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.fabricmc.loader.api.entrypoint.EntrypointContainer;
import net.fabricmc.loader.api.metadata.ModMetadata;
import net.minecraft.client.gui.screen.Screen;
import org.apache.logging.log4j.Level;

public class ModRegistry {
    private static final ModRegistry INSTANCE = new ModRegistry();

    private final Map<String, String> modNames = new HashMap<>();
    private final Map<String, ConfigScreenFactory<?>> configScreenFactories = new HashMap<>();
    private final Map<String, ConfigScreenFactory<?>> overridingConfigScreenFactories = new HashMap<>();

    private ModRegistry() {
    }

    public static ModRegistry getInstance() {
        return INSTANCE;
    }

    /* This needs to be done att the right time of loading the mod, so cannot be done in the constructor. */
    public void registerMods() {
        List<EntrypointContainer<Object>> modList =
                FabricLoader.getInstance().getEntrypointContainers("modmenu", Object.class);

        for (EntrypointContainer<Object> entryPoint : modList) {
            ModMetadata metadata = entryPoint.getProvider().getMetadata();
            String modId = metadata.getId();

            try {
                Object unknownApi = entryPoint.getEntrypoint();
                ModMenuApi modApi;

                if (unknownApi instanceof io.github.prospector.modmenu.api.ModMenuApi legacyApi) {
                    Main.LOGGER.log(Level.INFO,"Found legacy configurable mod: " + modId + ", " + metadata.getName());
                    modApi = new LegacyApiWrapper(legacyApi);
                } else if (unknownApi instanceof com.terraformersmc.modmenu.api.ModMenuApi modernApi) {
                    Main.LOGGER.log(Level.INFO,"Found configurable mod: " + modId + ", " + metadata.getName());
                    modApi = modernApi;
                } else {
                    Main.LOGGER.warn("Unknown Mod Menu API version for mod " + modId + ", class: " + unknownApi.getClass());
                    continue;
                }

                configScreenFactories.put(modId, modApi.getModConfigScreenFactory());
                Map<String, ConfigScreenFactory<?>> overridingFactories = modApi.getProvidedConfigScreenFactories();
                overridingConfigScreenFactories.putAll(overridingFactories);

                modNames.put(modId, metadata.getName());
                for (String overriddenModId: overridingFactories.keySet()) {
                    // We need to locate the proper mod from the modid to get the real name
                    Optional<ModContainer> container = FabricLoader.getInstance().getModContainer(overriddenModId);
                    if (container.isPresent()) {
                        String modName = container.get().getMetadata().getName();
                        Main.LOGGER.log(Level.INFO, "Found overridden config for mod: " + overriddenModId + ", " + modName);

                        modNames.put(overriddenModId, modName);
                    }
                }
            } catch (EntrypointException e) {
                Main.LOGGER.warn("Mod Menu API problem with mod " + modId, e);
            }
        }
    }

    public List<String> getAllModIds() {
        // Return mods sorted. This sorts on modID and not name, but is good enough.
        Comparator<String> sorter = Comparator.comparing(modId -> modId.toLowerCase(Locale.ROOT));

        // Fabric treats Vanilla ("minecraft") as a mod and returns the normal Options screen.
        // We don't want that so filter it out.
        return modNames.keySet().stream().sorted(sorter)
                .filter(modId -> !modId.equals("minecraft")).collect(Collectors.toList());
    }

    public String getModName(String modId) {
        return modNames.get(modId);
    }

    private Screen getScreen(String modid, Map<String, ConfigScreenFactory<?>> map, Screen parentScreen) {
        ConfigScreenFactory<?> factory = map.get(modid);
        return (factory != null) ? factory.create(parentScreen) : null;
    }

    public Screen getConfigScreen(String modid, Screen parentScreen) {
        Screen configScreen = getScreen(modid, configScreenFactories, parentScreen);
        if (configScreen == null) {
            configScreen = getScreen(modid, overridingConfigScreenFactories, parentScreen);
        }

        return configScreen;
    }
}
