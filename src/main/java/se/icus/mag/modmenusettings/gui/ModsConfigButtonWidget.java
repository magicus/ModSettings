package se.icus.mag.modmenusettings.gui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class ModsConfigButtonWidget extends ButtonWidget {
	public ModsConfigButtonWidget(int x, int y, int width, int height, Text text, Screen screen) {
		super(x, y, width, height, text, button -> MinecraftClient.getInstance().setScreen(new ModsConfigScreen(screen)));
	}
}
