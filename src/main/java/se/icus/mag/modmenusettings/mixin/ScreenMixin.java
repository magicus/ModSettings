package se.icus.mag.modmenusettings.mixin;

import net.fabricmc.fabric.api.client.screen.v1.Screens;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.AbstractParentElement;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.screen.GameMenuScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.text.TranslatableText;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import se.icus.mag.modmenusettings.ModMenuSettings;
import se.icus.mag.modmenusettings.gui.ModsConfigButtonWidget;

import java.util.List;

@Mixin(value = Screen.class, priority = 1100)
public abstract class ScreenMixin extends AbstractParentElement implements Drawable {
    @Shadow protected MinecraftClient client;
    @Shadow public int width;
    @Shadow public int height;

    @Inject(method = "init(Lnet/minecraft/client/MinecraftClient;II)V", at = @At("RETURN"))
    private void init(MinecraftClient client, int width, int height, CallbackInfo info) {
        ModMenuSettings.printButtonInfo((Screen)(Object)this);

        if ((Screen)(Object)this instanceof TitleScreen) {
            afterTitleScreenInit();
        } else if ((Screen)(Object)this instanceof GameMenuScreen) {
            afterGameMenuScreenInit();
        }
    }

    private void afterTitleScreenInit() {
        Screen screen = (Screen)(Object)this;
        ModMenuSettings.printButtonInfo(screen);
        final int buttonWidth = 204;

        final int spacing = 24;
        int savedY = 0;
        List<ClickableWidget> buttons = Screens.getButtons(screen);
        for (ClickableWidget button : buttons) {
            if (ModMenuSettings.buttonMatchesKey(button, "modmenu.title")) {
                button.setWidth(98);
                savedY = button.y;
            }
            // REALMS button: "menu.online"
        }
        ClickableWidget myButton = new ModsConfigButtonWidget(screen.width / 2 + 2, savedY, 98, 20, new TranslatableText("Mod Settings..."), screen);
        buttons.add(myButton);
    }

    private void afterGameMenuScreenInit() {
        final int buttonWidth = 204;
        final int spacing = 24;
        int yOffset = 0;
        boolean REMOVE_FEEDBACK = false;
        boolean REMOVE_BUGS = false;

        for (ClickableWidget button : Screens.getButtons((Screen)(Object)this)) {
            if (ModMenuSettings.buttonMatchesKey(button, "menu.sendFeedback")) {
                button.setWidth(buttonWidth/2);
            }
            if (REMOVE_FEEDBACK) {
                if (ModMenuSettings.buttonMatchesKey(button, "menu.sendFeedback")) {
                    button.visible = false;
                }
                if (!REMOVE_BUGS) {
                    if (ModMenuSettings.buttonMatchesKey(button, "menu.reportBugs")) {
                        button.setWidth(buttonWidth);
                        button.x = this.width / 2 - buttonWidth / 2;
                    }
                }
            }

            if (REMOVE_BUGS) {
                if (ModMenuSettings.buttonMatchesKey(button, "menu.reportBugs")) {
                    button.visible = false;
                    if (REMOVE_FEEDBACK) {
                        yOffset += spacing;
                    }
                }
                if (!REMOVE_FEEDBACK) {
                    if (ModMenuSettings.buttonMatchesKey(button, "menu.sendFeedback")) {
                        button.setWidth(buttonWidth);
                        button.x = this.width / 2 - buttonWidth / 2;
                    }
                }
            }
            button.y -= yOffset;
        }
    }

    }
