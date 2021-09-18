package io.github.prospector.modmenu.api;

import com.google.common.collect.ImmutableMap;
import com.terraformersmc.modmenu.util.ModMenuApiMarker;

import java.util.Map;

/**
 * Deprecated API, switch to {@link com.terraformersmc.modmenu.api.ModMenuApi} instead
 *
 * Will be removed in 1.18 snapshots
 */
@Deprecated
public interface ModMenuApi extends ModMenuApiMarker {
	/**
	 * THIS ENTIRE API IS DEPRECATED. MOVE TO {@link com.terraformersmc.modmenu.api.ModMenuApi}
	 * This API will be removed in 1.18 snapshots
	 *
	 * Used to construct a new config screen instance when your mod's
	 * configuration button is selected on the mod menu screen. The
	 * screen instance parameter is the active mod menu screen.
	 *
	 * @return A factory for constructing config screen instances.
	 */
	default ConfigScreenFactory<?> getModConfigScreenFactory() {
		return screen -> null;
	}

	/**
	 * THIS ENTIRE API IS DEPRECATED. MOVE TO {@link com.terraformersmc.modmenu.api.ModMenuApi}
	 * This API will be removed in 1.18 snapshots
	 *
	 * Used to provide config screen factories for other mods. This takes second
	 * priority to a mod's own config screen factory provider. For example, if
	 * mod `xyz` supplies a config screen factory, mod `abc` providing a config
	 * screen to `xyz` will be pointless, as the one provided by `xyz` will be
	 * used.
	 * <p>
	 * This method is NOT meant to be used to add a config screen factory to
	 * your own mod.
	 *
	 * @return a map of mod ids to screen factories.
	 */
	default Map<String, ConfigScreenFactory<?>> getProvidedConfigScreenFactories() {
		return ImmutableMap.of();
	}
}
