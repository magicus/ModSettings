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
        final int fullButtonWidth = 200;
        int shiftDown = 0;
        List<ClickableWidget> buttons = Screens.getButtons(screen);

        for (ClickableWidget button : Screens.getButtons(screen)) {

            if (buttonHasText(button, "modmenu.title") && button.getWidth() == fullButtonWidth) {
                // If we find a wide ModMenu button, shorten it and fit in our button on the same row
                button.setWidth(98);
                button.x = screen.width / 2 + 2;

                ClickableWidget msbutton = new ModsConfigButtonWidget(screen.width / 2 - fullButtonWidth / 2, button.y, 98, 20, screen);
                buttons.add(msbutton);
                return;
            }
            if (buttonHasText(button, "menu.options")) {
                // Otherwise put our button as full width, above "Options..."
                shiftDown = 24;

                // offset it 12 pixels up
                ClickableWidget msbutton = new ModsConfigButtonWidget(screen.width / 2 - fullButtonWidth / 2, button.y - 12, fullButtonWidth, 20, screen);
                buttons.add(msbutton);
            }

            if (!buttonHasText(button, "Mod Settings...")) {
                button.y += shiftDown;
            }
        }
    }

    public static void afterGameMenuScreenInit(GameMenuScreen screen) {
        final int fullButtonWidth = 204;
        int shiftDown = 0;
        List<ClickableWidget> buttons = Screens.getButtons(screen);

        for (ClickableWidget button : Screens.getButtons(screen)) {

            if (buttonHasText(button, "modmenu.title") && button.getWidth() == fullButtonWidth) {
                // If we find a wide ModMenu button, shorten it and fit in our button on the same row
                button.setWidth(98);
                button.x = screen.width / 2 + 4;

                ClickableWidget msbutton = new ModsConfigButtonWidget(screen.width / 2 - fullButtonWidth / 2, button.y, 98, 20, screen);
                buttons.add(msbutton);
                return;
            }
            if (buttonHasText(button, "menu.options")) {
                // Otherwise put our button as full width, above "Options..."
                shiftDown = 24;

                ClickableWidget msbutton = new ModsConfigButtonWidget(screen.width / 2 - fullButtonWidth / 2, button.y, fullButtonWidth, 20, screen);
                buttons.add(msbutton);
            }

            if (!buttonHasText(button, "Mod Settings...")) {
                button.y += shiftDown;
            }
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
