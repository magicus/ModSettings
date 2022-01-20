package se.icus.mag.modsettings;

import com.terraformersmc.modmenu.api.ModMenuApi;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import net.fabricmc.loader.api.EntrypointException;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.entrypoint.EntrypointContainer;
import net.fabricmc.loader.api.metadata.ModMetadata;
import net.minecraft.client.gui.screen.Screen;
import org.apache.logging.log4j.Level;

import java.util.*;
import java.util.stream.Collectors;

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
        List<EntrypointContainer<ModMenuApi>> modList =
                FabricLoader.getInstance().getEntrypointContainers("modmenu", ModMenuApi.class);

        for (EntrypointContainer<ModMenuApi> entryPoint : modList) {
            ModMetadata metadata = entryPoint.getProvider().getMetadata();
            String modId = metadata.getId();
            Main.LOGGER.log(Level.INFO,"Found configurable mod " + modId);

            try {
                ModMenuApi modApi = entryPoint.getEntrypoint();
                configScreenFactories.put(modId, modApi.getModConfigScreenFactory());
                overridingConfigScreenFactories.putAll(modApi.getProvidedConfigScreenFactories());
                modNames.put(modId, metadata.getName());
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

    public Screen getConfigScreen(String modid, Screen parentScreen) {
        ConfigScreenFactory<?> factory = configScreenFactories.getOrDefault(modid, overridingConfigScreenFactories.get(modid));
        if (factory != null) {
            return factory.create(parentScreen);
        }

        return null;
    }
}
