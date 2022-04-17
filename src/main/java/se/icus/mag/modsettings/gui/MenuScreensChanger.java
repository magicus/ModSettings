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
        injectModSettingsButton(screen, TITLE_FULL_BUTTON_WIDTH, 2,  BUTTON_VERICAL_SPACING / 2);
    }

    public static void postGameMenuScreenInit(GameMenuScreen screen) {
        injectModSettingsButton(screen, INGAME_FULL_BUTTON_WIDTH, 4,  0);
    }

    private static void injectModSettingsButton(Screen screen, int fullButtonWidth,
        int halfButtonSpacer, int verticalOffset) {
        List<ClickableWidget> buttons = Screens.getButtons(screen);
        boolean shortenModMenu = false;
        ClickableWidget savedButton = null;

        // First scout the menu to find best way to inject our button
        for (ClickableWidget button : buttons) {
            if (buttonHasText(button, "modmenu.title") && button.getWidth() == fullButtonWidth) {
                savedButton = button;
                shortenModMenu = true;
                // This is the preferred method, so break out if we found this
                break;
            }

            if (buttonHasText(button, "menu.options")) {
                // Otherwise put our button as full width, above "Options..." and shift all remaining buttons down
                savedButton = button;
            }
        }

        if (shortenModMenu) {
            // If we find a wide ModMenu button, shorten it and fit in our button on the same row
            savedButton.setWidth(HALF_BUTTON_WIDTH);
            savedButton.x = screen.width / 2 + halfButtonSpacer;

            ClickableWidget msbutton = new ModSettingsButton(screen.width / 2 - fullButtonWidth / 2,
                savedButton.y, HALF_BUTTON_WIDTH, BUTTON_HEIGHT, screen);
            buttons.add(msbutton);
        } else {
            if (savedButton == null) {
                // There is no "Options..." button. Just grab an arbitrary button
                savedButton = buttons.get(0);
            }
            // Shift all buttons starting at "Options..." down
            int optionsY = savedButton.y;
            for (ClickableWidget button : buttons) {
                if (button.y >= optionsY) {
                    button.y += BUTTON_VERICAL_SPACING;
                }
            }

            // Put our button as full width, where "Options..." used to be
            ClickableWidget msbutton = new ModSettingsButton(screen.width / 2 - fullButtonWidth / 2,
                optionsY - verticalOffset, fullButtonWidth, BUTTON_HEIGHT, screen);
            buttons.add(msbutton);
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
