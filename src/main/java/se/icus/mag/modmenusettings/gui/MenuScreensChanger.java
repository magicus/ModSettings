package se.icus.mag.modmenusettings.gui;

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

    public static void afterTitleScreenInit(TitleScreen screen) {
        List<ClickableWidget> buttons = Screens.getButtons(screen);

        final int buttonWidth = 204;

        final int spacing = 24;
        int savedY = 0;
        int maxY = 0;
        for (ClickableWidget button : buttons) {
            if (button.y > maxY) maxY = button.y;
            if (buttonHasText(button, "modmenu.title")) {
                button.setWidth(98);
                savedY = button.y;
            }
            // REALMS button: "menu.online"
        }
        ClickableWidget myButton = new ModsConfigButtonWidget(screen.width / 2 + 2, savedY, 98, 20, screen);
        buttons.add(myButton);
        ClickableWidget myButton2 = new ModsConfigButtonWidget(screen.width / 2 + 2, maxY + 24, 98, 20, screen);
        buttons.add(myButton2);
    }

    public static void afterGameMenuScreenInit(GameMenuScreen screen) {
        final int buttonWidth = 204;
        final int spacing = 24;
        int yOffset = 0;
        boolean REMOVE_FEEDBACK = false;
        boolean REMOVE_BUGS = false;

        for (ClickableWidget button : Screens.getButtons(screen)) {

            if (buttonHasText(button, "menu.sendFeedback")) {
                button.setWidth(buttonWidth / 2);
            }
            if (REMOVE_FEEDBACK) {

                if (buttonHasText(button, "menu.sendFeedback")) {
                    button.visible = false;
                }
                if (!REMOVE_BUGS) {

                    if (buttonHasText(button, "menu.reportBugs")) {
                        button.setWidth(buttonWidth);
                        button.x = screen.width / 2 - buttonWidth / 2;
                    }
                }
            }

            if (REMOVE_BUGS) {

                if (buttonHasText(button, "menu.reportBugs")) {
                    button.visible = false;
                    if (REMOVE_FEEDBACK) {
                        yOffset += spacing;
                    }
                }
                if (!REMOVE_FEEDBACK) {

                    if (buttonHasText(button, "menu.sendFeedback")) {
                        button.setWidth(buttonWidth);
                        button.x = screen.width / 2 - buttonWidth / 2;
                    }
                }
            }
            button.y -= yOffset;
        }
    }

    private static boolean buttonHasText(ClickableWidget button, String translationKey) {
        Text text = button.getMessage();
        return text instanceof TranslatableText && ((TranslatableText) text).getKey().equals(translationKey);
    }

    public static class ModsConfigButtonWidget extends ButtonWidget {
        public ModsConfigButtonWidget(int x, int y, int width, int height, Screen screen) {
            super(x, y, width, height, new TranslatableText("Mod Settings..."),
                    button -> MinecraftClient.getInstance().setScreen(new ModsConfigScreen(screen)));
        }
    }
}
