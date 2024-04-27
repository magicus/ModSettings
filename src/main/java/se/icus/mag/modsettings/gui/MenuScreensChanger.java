package se.icus.mag.modsettings.gui;

import java.util.List;
import net.fabricmc.fabric.api.client.screen.v1.Screens;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.text.Text;
import se.icus.mag.modsettings.gui.screen.ModSettingsScreen;
import se.icus.mag.modsettings.gui.widget.Button;

public abstract class MenuScreensChanger {
    private static final int TITLE_FULL_BUTTON_WIDTH = 200;
    private static final int INGAME_FULL_BUTTON_WIDTH = 204;
    private static final int HALF_BUTTON_WIDTH = 98;
    private static final int BUTTON_HEIGHT = 20;
    private static final int BUTTON_VERICAL_SPACING = 24;

    public static void postTitleScreenInit(Screen screen) {
        List<ClickableWidget> buttons = Screens.getButtons(screen);
        injectModSettingsButton(screen, buttons, TITLE_FULL_BUTTON_WIDTH, 2, BUTTON_VERICAL_SPACING / 2);
    }

    public static void postGameMenuScreenInit(Screen screen) {
        List<ClickableWidget> buttons = Screens.getButtons(screen);
        // If this is the Pause game menu (F3 + Esc), do not inject anything
        if (buttons.size() == 1 && buttonHasText(buttons.get(0), "menu.paused")) return;

        injectModSettingsButton(screen, buttons, INGAME_FULL_BUTTON_WIDTH, 4, 0);
    }

    private static void injectModSettingsButton(Screen screen, List<ClickableWidget> buttons, int fullButtonWidth,
        int halfButtonSpacer, int verticalOffset) {
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
            savedButton.setX(screen.width / 2 + halfButtonSpacer);

            ClickableWidget msbutton = new ModSettingsButton(screen.width / 2 - fullButtonWidth / 2,
                savedButton.getY(), HALF_BUTTON_WIDTH, BUTTON_HEIGHT, screen);
            buttons.add(msbutton);
        } else {
            if (savedButton == null) {
                // There is no "Options..." button. Just grab an arbitrary button
                savedButton = buttons.get(0);
            }
            // Shift all buttons starting at "Options..." down
            int optionsY = savedButton.getY();
            for (ClickableWidget button : buttons) {
                if (button.getY() >= optionsY) {
                    button.setY(button.getY() + BUTTON_VERICAL_SPACING);
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
        return text.getContent().equals(Text.translatable(translationKey).getContent());
    }

    public static class ModSettingsButton extends Button {
        public ModSettingsButton(int x, int y, int width, int height, Screen screen) {
            super(x, y, width, height, Text.translatable("modsettings.button.title"),
                    button -> MinecraftClient.getInstance().setScreen(new ModSettingsScreen(screen)));
        }
    }
}
