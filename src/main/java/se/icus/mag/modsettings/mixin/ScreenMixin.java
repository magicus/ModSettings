package se.icus.mag.modsettings.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.AbstractParentElement;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.screen.GameMenuScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import se.icus.mag.modsettings.gui.MenuScreensChanger;

// Set priority so we run after ModMenu (1000) and MinimalMenu (1100)
@Mixin(value = Screen.class, priority = 1500)
public abstract class ScreenMixin extends AbstractParentElement implements Drawable {

    @Inject(method = "init(Lnet/minecraft/client/MinecraftClient;II)V", at = @At("RETURN"))
    private void init(MinecraftClient client, int width, int height, CallbackInfo info) {
        Screen screen = (Screen) (Object) this;

        if (screen instanceof TitleScreen titleScreen) {
            MenuScreensChanger.postTitleScreenInit(titleScreen);
        } else if (screen instanceof GameMenuScreen gameMenuScreen) {
            MenuScreensChanger.postGameMenuScreenInit(gameMenuScreen);
        }
    }
}
