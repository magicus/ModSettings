package se.icus.mag.modmenusettings;

import com.terraformersmc.modmenu.api.ModMenuApi;
import com.terraformersmc.modmenu.util.ModMenuApiMarker;
import io.github.prospector.modmenu.api.ConfigScreenFactory;
import net.fabricmc.loader.api.EntrypointException;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.entrypoint.EntrypointContainer;
import net.fabricmc.loader.api.metadata.ModMetadata;
import net.minecraft.client.gui.screen.Screen;
import org.apache.logging.log4j.Level;

import java.util.*;
import java.util.stream.Collectors;

public class ModRegistry {
    private static final Map<String, String> CONFIGABLE_MODS_NAMES = new HashMap<>();
    private static final Map<String, ConfigScreenFactory<?>> factories = new HashMap<>();
    private static final Map<String, ConfigScreenFactory<?>> overridingFactories = new HashMap<>();

    public static void registerMods() {
        List<EntrypointContainer<ModMenuApiMarker>> modList = FabricLoader.getInstance().getEntrypointContainers("modmenu", ModMenuApiMarker.class);

        for (EntrypointContainer<ModMenuApiMarker> entryPoint : modList) {
            ModMetadata metadata = entryPoint.getProvider().getMetadata();
            String modId = metadata.getId();
            ModMenuSettings.LOGGER.log(Level.INFO,"found mod1: " + modId);

            try {
                ModMenuApiMarker marker = entryPoint.getEntrypoint();
                if (marker instanceof ModMenuApi) {
                    ModMenuApi marker2 = (ModMenuApi) marker;

                    factories.put(modId, marker2.getModConfigScreenFactory());
                    overridingFactories.putAll(marker2.getProvidedConfigScreenFactories());
                } else  if (marker instanceof io.github.prospector.modmenu.api.ModMenuApi) {
                    io.github.prospector.modmenu.api.ModMenuApi marker2 = (io.github.prospector.modmenu.api.ModMenuApi) marker;

                    factories.put(modId, marker2.getModConfigScreenFactory());
                    overridingFactories.putAll(marker2.getProvidedConfigScreenFactories());
                } else {
                    ModMenuSettings.LOGGER.warn("class problem with " + modId);
                    continue;
                }
                CONFIGABLE_MODS_NAMES.put(modId, metadata.getName());
            } catch (EntrypointException e) {
                // Ignore incompatible mods, they are either broken or implement the old API
                ModMenuSettings.LOGGER.warn("problem with " + modId + e);
            }
        }
    }

    public static List<String> getAllModIds() {
        Comparator<String> sorter = Comparator.comparing(modId -> modId.toLowerCase(Locale.ROOT));

        return CONFIGABLE_MODS_NAMES.keySet().stream().sorted(sorter)
                .filter(modId -> !modId.equals("minecraft")).collect(Collectors.toList());
    }

    public static String getModName(String modId) {
        return CONFIGABLE_MODS_NAMES.get(modId);
    }

    public static Screen getConfigScreen(String modid, Screen menuScreen) {
        ConfigScreenFactory<?> factory = factories.getOrDefault(modid, overridingFactories.get(modid));
        if (factory != null) {
            return factory.create(menuScreen);
        }

        return null;
    }
}