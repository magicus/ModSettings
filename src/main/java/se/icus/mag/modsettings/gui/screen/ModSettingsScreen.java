package se.icus.mag.modsettings.gui.screen;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;
import se.icus.mag.modsettings.Main;
import se.icus.mag.modsettings.ModRegistry;

import java.util.LinkedList;
import java.util.List;
import se.icus.mag.modsettings.gui.widget.Button;
import se.icus.mag.modsettings.gui.widget.ModListWidget;

public class ModSettingsScreen extends TitledScreen {
	private static final int FULL_BUTTON_WIDTH = 200;
	private static final int BUTTON_HEIGHT = 20;

	private ModListWidget list;
	private boolean initIsProcessing;

	public ModSettingsScreen(Screen previous) {
		super(Text.translatable("modsettings.screen.title"), previous);
	}

	@Override
	protected void init() {
		// Protect against mods like Content Creator Integration that triggers
		// a recursive call of Screen.init() while creating the settings screen...
		if (initIsProcessing) return;
		initIsProcessing = true;

		// Put list between 32 pixels from top and bottom
		this.list = new ModListWidget(this.client, this.width, this.height - 64,  32, 25);
		this.list.addAll(getAllModConfigOptions());

		this.addSelectableChild(this.list);
		this.addDrawableChild(this.list);
		this.addDrawableChild(new Button(this.width / 2 - FULL_BUTTON_WIDTH / 2, this.height - 27,
				FULL_BUTTON_WIDTH, BUTTON_HEIGHT, ScreenTexts.DONE,
				button -> this.client.setScreen(this.previous)));
		initIsProcessing = false;
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

	public record ModSettingsOption(String modId, String modName, Screen configScreen)  {
	}
}
