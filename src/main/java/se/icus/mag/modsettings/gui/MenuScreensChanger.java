package se.icus.mag.modsettings.gui;

import net.fabricmc.fabric.api.client.screen.v1.Screens;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.GameMenuScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

import java.util.List;

public abstract class MenuScreensChanger {
    private static final int TITLE_FULL_BUTTON_WIDTH = 200;
    private static final int INGAME_FULL_BUTTON_WIDTH = 204;
    private static final int HALF_BUTTON_WIDTH = 98;
    private static final int BUTTON_HEIGHT = 20;
    private static final int BUTTON_VERICAL_SPACING = 24;

    public static void postTitleScreenInit(TitleScreen screen) {
        List<ClickableWidget> buttons = Screens.getButtons(screen);
        int shiftDown = 0;

        for (ClickableWidget button : buttons) {
            if (buttonHasText(button, "modmenu.title") && button.getWidth() == TITLE_FULL_BUTTON_WIDTH) {
                // If we find a wide ModMenu button, shorten it and fit in our button on the same row
                button.setWidth(HALF_BUTTON_WIDTH);
                button.x = screen.width / 2 + 2;

                ClickableWidget msbutton = new ModSettingsButton(screen.width / 2 - TITLE_FULL_BUTTON_WIDTH / 2, button.y, HALF_BUTTON_WIDTH, BUTTON_HEIGHT, screen);
                buttons.add(msbutton);
                return;
            }

            if (buttonHasText(button, "menu.options")) {
                // Otherwise put our button as full width, above "Options..." and shift all remaining buttons down
                shiftDown = BUTTON_VERICAL_SPACING;

                // Offset it 12 pixels up
                ClickableWidget msbutton = new ModSettingsButton(screen.width / 2 - TITLE_FULL_BUTTON_WIDTH / 2, button.y - 12, TITLE_FULL_BUTTON_WIDTH, BUTTON_HEIGHT, screen);
                buttons.add(msbutton);
            }

            if (!buttonHasText(button, "modsettings.button.title")) {
                button.y += shiftDown;
            }
        }
    }

    public static void postGameMenuScreenInit(GameMenuScreen screen) {
        List<ClickableWidget> buttons = Screens.getButtons(screen);
        int shiftDown = 0;

        for (ClickableWidget button : buttons) {
            if (buttonHasText(button, "modmenu.title") && button.getWidth() == INGAME_FULL_BUTTON_WIDTH) {
                // If we find a wide ModMenu button, shorten it and fit in our button on the same row
                button.setWidth(HALF_BUTTON_WIDTH);
                button.x = screen.width / 2 + 4;

                ClickableWidget msbutton = new ModSettingsButton(screen.width / 2 - INGAME_FULL_BUTTON_WIDTH / 2, button.y, HALF_BUTTON_WIDTH, BUTTON_HEIGHT, screen);
                buttons.add(msbutton);
                return;
            }

            if (buttonHasText(button, "menu.options")) {
                // Otherwise put our button as full width, above "Options..." and shift all remaining buttons down
                shiftDown = BUTTON_VERICAL_SPACING;

                ClickableWidget msbutton = new ModSettingsButton(screen.width / 2 - INGAME_FULL_BUTTON_WIDTH / 2, button.y, INGAME_FULL_BUTTON_WIDTH, BUTTON_HEIGHT, screen);
                buttons.add(msbutton);
            }

            if (!buttonHasText(button, "modsettings.button.title")) {
                button.y += shiftDown;
            }
        }
    }

    private static boolean buttonHasText(ClickableWidget button, String translationKey) {
        Text text = button.getMessage();
        return text instanceof TranslatableText && ((TranslatableText) text).getKey().equals(translationKey);
    }

    public static class ModSettingsButton extends ButtonWidget {
        public ModSettingsButton(int x, int y, int width, int height, Screen screen) {
            super(x, y, width, height, new TranslatableText("modsettings.button.title"),
                    button -> MinecraftClient.getInstance().setScreen(new ModSettingsScreen(screen)));
        }
    }
}
