package com.terraformersmc.modmenu.util;

import io.github.prospector.modmenu.api.ConfigScreenFactory;
import java.util.Map;

public interface ModMenuApiMarker {
   io.github.prospector.modmenu.api.ConfigScreenFactory<?> getModConfigScreenFactory();

  Map<String, ConfigScreenFactory<?>> getProvidedConfigScreenFactories();

}
