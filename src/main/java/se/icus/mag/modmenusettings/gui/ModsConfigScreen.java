package se.icus.mag.modmenusettings.gui;

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
import se.icus.mag.modmenusettings.ModMenuSettings;
import se.icus.mag.modmenusettings.ModRegistry;

import java.util.LinkedList;
import java.util.List;

public class ModsConfigScreen extends Screen {
	private final Screen previous;
	private ButtonListWidget list;

	public ModsConfigScreen(Screen previous) {
		super(new TranslatableText("Mod Settings"));
		this.previous = previous;
	}

	protected void init() {
		this.list = new ButtonListWidget(this.client, this.width, this.height, 32, this.height - 32, 25);
		this.list.addAll(getAllModConfigOptions());

		this.addSelectableChild(this.list);
		this.addDrawableChild(new ButtonWidget(this.width / 2 - 100, this.height - 27, 200, 20,
				ScreenTexts.DONE, (button) -> this.client.setScreen(this.previous)));
	}

	private Option[] getAllModConfigOptions() {
		List<Option> options = new LinkedList<>();
		for (String modId : ModRegistry.getAllModIds()) {
			try {
				Screen configScreen = ModRegistry.getConfigScreen(modId, this);
				if (configScreen != null) {
					options.add(new ModConfigOption(modId, ModRegistry.getModName(modId), configScreen));
				}
			} catch (Throwable e) {
				ModMenuSettings.LOGGER.error("Error creating Settings screen from mod " + modId, e);
			}
		}
		return options.toArray(new Option[0]);
	}

	public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		this.renderBackground(matrices);
		this.list.render(matrices, mouseX, mouseY, delta);
		drawCenteredText(matrices, this.textRenderer, this.title, this.width / 2, 5, 0xffffff);
		super.render(matrices, mouseX, mouseY, delta);
	}

	public void onClose() {
		this.client.setScreen(this.previous);
	}

	class ModConfigOption extends Option {
		private final String modName;
		private final Screen configScreen;

		public ModConfigOption(String modId, String modName, Screen configScreen) {
			super(modId);
			this.modName = modName;
			this.configScreen = configScreen;
		}

		@Override
		public ClickableWidget createButton(GameOptions options, int x, int y, int width) {
			return new ButtonWidget(x, y, width, 20, Text.of(this.modName), this::onPress);
		}

		private void onPress(ButtonWidget buttonWidget) {
			client.setScreen(this.configScreen);
		}
	}
}
