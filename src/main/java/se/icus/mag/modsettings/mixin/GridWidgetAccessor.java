package se.icus.mag.modsettings.mixin;

import java.util.List;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.layouts.GridLayout;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(GridLayout.class)
public interface GridWidgetAccessor {
	@Accessor
	List<AbstractWidget> getChildren();
}
