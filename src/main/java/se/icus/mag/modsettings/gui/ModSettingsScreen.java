package se.icus.mag.modsettings.gui;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.options.OptionsSubScreen;
import net.minecraft.network.chat.Component;
import se.icus.mag.modsettings.Main;
import se.icus.mag.modsettings.ModRegistry;

public class ModSettingsScreen extends OptionsSubScreen {
	private static final int BUTTON_WIDTH = 150;
	private static final int BUTTON_HEIGHT = 20;
	private boolean initIsProcessing;

	public ModSettingsScreen(Screen previous) {
		super(previous, Minecraft.getInstance().options, Component.translatable("modsettings.screen.title"));
	}

	@Override
	protected void init() {
		// Protect against mods like Content Creator Integration that triggers
		// a recursive call of Screen.init() while creating the settings screen...
		if (initIsProcessing) return;
		initIsProcessing = true;
		try {
			super.init();
		} finally {
			initIsProcessing = false;
		}
	}

	@Override
	protected void addOptions() {
		ModSettingsOption[] options = getAllModConfigOptions();
		List<AbstractWidget> buttons = Arrays.stream(options)
				.map(option -> new Button(0, 0, BUTTON_WIDTH, BUTTON_HEIGHT, Component.nullToEmpty(option.modName()),
						button -> this.minecraft.setScreen(option.configScreen())))
				.collect(Collectors.toList());
		this.list.addSmall(buttons);
	}

	private ModSettingsOption[] getAllModConfigOptions() {
		List<ModSettingsOption> options = new LinkedList<>();
		for (String modId : ModRegistry.getInstance().getAllModIds()) {
			try {
				Screen configScreen = ModRegistry.getInstance().getConfigScreen(modId, this);
				if (configScreen != null) {
					options.add(new ModSettingsOption(modId, ModRegistry.getInstance().getModName(modId), configScreen));
				}
			} catch (Throwable e) {
				Main.LOGGER.error("Error creating Settings screen from mod " + modId, e);
			}
		}
		return options.toArray(new ModSettingsOption[0]);
	}

	public record ModSettingsOption(String modId, String modName, Screen configScreen) {
	}
}
