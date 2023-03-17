package se.icus.mag.modsettings.mixin;

import net.minecraft.client.gui.AbstractParentElement;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.screen.GameMenuScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import se.icus.mag.modsettings.gui.MenuScreensChanger;

// Set priority so we run after ModMenu (1000) and MinimalMenu (1100)
@Mixin(value = GameMenuScreen.class, priority = 1500)
public abstract class GameMenuScreenMixin extends AbstractParentElement implements Drawable {
    @Inject(method = "init()V", at = @At("RETURN"))
    private void postInit(CallbackInfo info) {
        GameMenuScreen screen = (GameMenuScreen) (Object) this;
        MenuScreensChanger.postGameMenuScreenInit(screen);
    }
}
