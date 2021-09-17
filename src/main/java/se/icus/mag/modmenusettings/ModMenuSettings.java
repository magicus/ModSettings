package se.icus.mag.modmenusettings;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.screen.v1.Screens;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Language;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class ModMenuSettings implements ClientModInitializer {
    public static final String MOD_ID = "modmenusettings";
    public static final String MOD_NAME = "ModMenuSettings";

    public static final Logger LOGGER = LogManager.getLogger("Mod Menu Settings");

    @Override
    public void onInitializeClient() {
        ModRegistry.registerMods();
    }

    public static void log(Level level, String message){
        LOGGER.log(level, "["+MOD_NAME+"] " + message);
    }

    public static void printButtonInfo(Screen screen) {
        log(Level.INFO,"-------------------------------------");
        log(Level.INFO, screen.getClass().getName());

        Language language = Language.getInstance();
        List<ClickableWidget> buttons = Screens.getButtons((Screen)(Object)screen);
        for (ClickableWidget button : buttons) {
            Text buttonMessage = button.getMessage();
            String buttonText = buttonMessage.getString();

            log(Level.INFO,"-------------------------------------");
            log(Level.INFO, "Button localized: " + buttonText);
            log(Level.INFO, "Button index:     " + buttons.indexOf(button));

            if (buttonMessage instanceof TranslatableText) {
                String buttonLangKey = ((TranslatableText) buttonMessage).getKey();
                String buttonLangText = language.get(buttonLangKey);

                log(Level.INFO, "Button key:       " + buttonLangKey);
                if (!buttonText.equals(buttonLangText)) {
                    log(Level.INFO, "Button text:      " + buttonLangText);
                }

                Object[] textArgs = ((TranslatableText) buttonMessage).getArgs();
                for (int i = 0; i < textArgs.length; i++) {
                    Object arg = textArgs[i];
                    if (arg instanceof TranslatableText) {
                        String argKey = ((TranslatableText) arg).getKey();
                        log(Level.INFO, "");
                        log(Level.INFO, "Text arg " + i + " key:   " + argKey);
                        log(Level.INFO, "Text arg " + i + " text:  " + language.get(argKey));
                    }
                }
            }
        }
        log(Level.INFO,"-------------------------------------");
    }

    private static boolean buttonHasText(ClickableWidget button, String translationKey) {
        Text text = button.getMessage();
        return text instanceof TranslatableText && ((TranslatableText) text).getKey().equals(translationKey);
    }

    public static boolean buttonMatchesKey(ClickableWidget button, String key) {
        Text buttonMessage = button.getMessage();
        if (buttonMessage instanceof TranslatableText) {
            String buttonKey = ((TranslatableText) buttonMessage).getKey();
            if (buttonKey.equals(key)) {
                return true;
            }
            Object[] textArgs = ((TranslatableText) buttonMessage).getArgs();
            for (Object arg : textArgs) {
                if (arg instanceof TranslatableText) {
                    String argKey = ((TranslatableText) arg).getKey();
                    if (argKey.equals(key)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
