package se.icus.mag.modsettings.mixin;

import java.util.List;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.GridWidget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(GridWidget.class)
public interface GridWidgetAccessor {
	@Accessor
	List<ClickableWidget> getChildren();
}
