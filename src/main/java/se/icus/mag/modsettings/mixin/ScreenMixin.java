package se.icus.mag.modsettings.mixin;

import net.minecraft.client.gui.screens.PauseScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import se.icus.mag.modsettings.gui.MenuScreensChanger;

// Set priority so we run after Fabric API, used by ModMenu, which uses
// the default priority (1000). We also avoid conflict with MinimalMenu
// which hooks into TitleScreen.init(), which is called as part of Screen.init()
@Mixin(value = Screen.class, priority = 1500)
public abstract class ScreenMixin {

    @Inject(method = "init(II)V", at = @At("TAIL"))
    private void init(int width, int height, CallbackInfo info) {
        Screen screen = (Screen) (Object) this;

        if (screen instanceof TitleScreen titleScreen) {
            MenuScreensChanger.postTitleScreenInit(titleScreen);
        } else if (screen instanceof PauseScreen gameMenuScreen) {
            MenuScreensChanger.postGameMenuScreenInit(gameMenuScreen);
        }
    }
}
