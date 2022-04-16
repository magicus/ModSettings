package se.icus.mag.modsettings.gui;

import com.terraformersmc.modmenu.gui.ModsScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ScreenTexts;
import net.minecraft.client.gui.widget.ButtonListWidget;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.Option;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import se.icus.mag.modsettings.Main;
import se.icus.mag.modsettings.ModRegistry;

import java.util.LinkedList;
import java.util.List;

public class ModSettingsScreen extends ModsScreen {
	private static final int FULL_BUTTON_WIDTH = 200;
	private static final int BUTTON_HEIGHT = 20;
	private static final int TITLE_COLOR = 0xffffff;

	private final Screen previous;
	private ButtonListWidget list;

	public ModSettingsScreen(Screen previous) {
		super(new TranslatableText("modsettings.screen.title"));
		this.previous = previous;
	}

	@Override
	protected void init() {
		// Put list between 32 pixels from top and bottom
		this.list = new ButtonListWidget(this.client, this.width, this.height, 32, this.height - 32, 25);
		this.list.addAll(getAllModConfigOptions());

		this.addSelectableChild(this.list);
		this.addDrawableChild(new ButtonWidget(this.width / 2 - FULL_BUTTON_WIDTH / 2, this.height - 27,
				FULL_BUTTON_WIDTH, BUTTON_HEIGHT, ScreenTexts.DONE,
				button -> this.client.setScreen(this.previous)));
	}

	private Option[] getAllModConfigOptions() {
		List<Option> options = new LinkedList<>();
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
		return options.toArray(new Option[0]);
	}

	@Override
	public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		this.renderBackground(matrices);
		this.list.render(matrices, mouseX, mouseY, delta);
		drawCenteredText(matrices, this.textRenderer, this.title, this.width / 2, 5, TITLE_COLOR);
		super.render(matrices, mouseX, mouseY, delta);
	}

	@Override
	public void onClose() {
		this.client.setScreen(this.previous);
	}

	public class ModSettingsOption extends Option {
		private final String modName;
		private final Screen configScreen;

		public ModSettingsOption(String modId, String modName, Screen configScreen) {
			super(modId);
			this.modName = modName;
			this.configScreen = configScreen;
		}

		@Override
		public ClickableWidget createButton(GameOptions options, int x, int y, int width) {
			return new ButtonWidget(x, y, width, BUTTON_HEIGHT, Text.of(this.modName),
					button -> client.setScreen(this.configScreen));
		}
	}
}
