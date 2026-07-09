package se.icus.mag.modsettings.gui;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.Positioner;
import net.minecraft.client.gui.widget.TextWidget;
import net.minecraft.client.gui.widget.ThreePartsLayoutWidget;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;
import se.icus.mag.modsettings.Main;
import se.icus.mag.modsettings.ModRegistry;

import java.util.LinkedList;
import java.util.List;

public class ModSettingsScreen extends Screen {
	private static final int FULL_BUTTON_WIDTH = 200;
	private static final int BUTTON_HEIGHT = 20;
	private static final int LIST_TOP_MARGIN = 32;
	private static final int LIST_BOTTOM_MARGIN = 32;

	private final Screen previous;
	private ThreePartsLayoutWidget layout;
	private ModListWidget list;
	private boolean initIsProcessing;

	public ModSettingsScreen(Screen previous) {
		super(Text.translatable("modsettings.screen.title"));
		this.previous = previous;
	}

	@Override
	protected void init() {
		// Protect against mods like Content Creator Integration that triggers
		// a recursive call of Screen.init() while creating the settings screen...
		if (initIsProcessing) return;
		initIsProcessing = true;

		this.layout = new ThreePartsLayoutWidget(this, LIST_TOP_MARGIN, LIST_BOTTOM_MARGIN);
		this.layout.addHeader(new TextWidget(this.title, this.textRenderer), Positioner::alignHorizontalCenter);

		this.list = new ModListWidget(this.client, this.width, this.height - LIST_TOP_MARGIN - LIST_BOTTOM_MARGIN, LIST_TOP_MARGIN, 25);
		this.list.addAll(getAllModConfigOptions());
		this.layout.addBody(this.list);

		this.layout.addFooter(new Button(0, 0, FULL_BUTTON_WIDTH, BUTTON_HEIGHT, ScreenTexts.DONE,
				button -> this.client.setScreen(this.previous)), Positioner::alignHorizontalCenter);
		this.layout.forEachChild(child -> this.addDrawableChild(child));
		this.refreshWidgetPositions();
		initIsProcessing = false;
	}

	@Override
	protected void refreshWidgetPositions() {
		if (this.layout != null) {
			this.layout.refreshPositions();
		}
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

	@Override
	public void close() {
		this.client.setScreen(this.previous);
	}

	public record ModSettingsOption(String modId, String modName, Screen configScreen)  {
	}
}
