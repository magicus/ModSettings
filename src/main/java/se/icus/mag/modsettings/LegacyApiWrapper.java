package se.icus.mag.modsettings;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import java.util.Map;
import java.util.stream.Collectors;
import net.minecraft.client.gui.screen.Screen;

public class LegacyApiWrapper implements ModMenuApi {
  private io.github.prospector.modmenu.api.ModMenuApi legacyApi;

  public LegacyApiWrapper(io.github.prospector.modmenu.api.ModMenuApi legacyApi) {
    this.legacyApi = legacyApi;
  }

  @Override
  public ConfigScreenFactory<?> getModConfigScreenFactory() {
    return new LegacyScreenFactory(legacyApi.getModConfigScreenFactory());
  }

  @Override
  public Map<String, ConfigScreenFactory<?>> getProvidedConfigScreenFactories() {
    Map<String, io.github.prospector.modmenu.api.ConfigScreenFactory<?>> legacyMap = legacyApi.getProvidedConfigScreenFactories();

    return legacyMap.keySet().stream().collect(Collectors.toMap(id -> id,
        id -> new LegacyScreenFactory(legacyMap.get(id))));
  }

  public static class LegacyScreenFactory<S extends Screen> implements ConfigScreenFactory<S> {
    private io.github.prospector.modmenu.api.ConfigScreenFactory<S> legcayFactory;

    public LegacyScreenFactory(io.github.prospector.modmenu.api.ConfigScreenFactory legcayFactory) {
      this.legcayFactory = legcayFactory;
    }

    @Override
    public S create(Screen parent) {
      return legcayFactory.create(parent);
    }
  }
}
