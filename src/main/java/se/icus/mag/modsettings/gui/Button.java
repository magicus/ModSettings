package se.icus.mag.modsettings.gui;

import net.minecraft.network.chat.Component;

public class Button extends net.minecraft.client.gui.components.Button.Plain {
    public Button(int x, int y, int width, int height, Component message, OnPress onPress) {
        super(x, y, width, height, message, onPress, DEFAULT_NARRATION);
    }
}
