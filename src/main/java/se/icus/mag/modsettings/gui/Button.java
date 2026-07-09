package se.icus.mag.modsettings.gui;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.ButtonWidget;

public class Button extends ButtonWidget.Text {
    public Button(int x, int y, int width, int height, net.minecraft.text.Text message, PressAction onPress) {
        super(x, y, width, height, message, onPress, DEFAULT_NARRATION_SUPPLIER);
    }
}
