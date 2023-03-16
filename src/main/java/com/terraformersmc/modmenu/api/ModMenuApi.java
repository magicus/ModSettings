package com.terraformersmc.modmenu.api;

import com.google.common.collect.ImmutableMap;
import java.util.Map;
import java.util.function.Consumer;

public interface ModMenuApi {
	/**
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

	/**
	 * Used to mark mods with a badge indicating that they are
	 * provided by a modpack.
	 * <p>
	 * Builtin mods such as `minecraft` cannot be marked as
	 * provided by a modpack.
	 */
	default void attachModpackBadges(Consumer<String> consumer) {
		return;
	}
}
