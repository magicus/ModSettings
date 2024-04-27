package se.icus.mag.modsettings.gui.widget;

import java.util.List;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.util.Identifier;

public class IconToggleButtonWidget extends IconButtonWidget {
    private final List<Identifier> textures;
    private final List<Tooltip> tooltips;
    private final ToggleAction onChange;

    private int selection;

    public IconToggleButtonWidget(int x, int y, int width, int height, int textureWidth, int textureHeight,
                                  List<Identifier> textures, List<Tooltip> tooltips, int selection, ToggleAction onChange) {
        super(x, y, width, height, textureWidth, textureHeight, ButtonWidget::onPress);

        this.textures = textures;
        this.tooltips = tooltips;
        this.onChange = onChange;
        this.selection = selection;
        updateSelection();
    }

    @Override
    public void onPress() {
        this.selection = (this.selection + 1) % this.textures.size();
        updateSelection();
        this.onChange.onChange(selection);
    }

    private void updateSelection() {
        this.texture = this.textures.get(this.selection);
        this.setTooltip(this.tooltips.get(this.selection));
    }

    @FunctionalInterface
    public interface ToggleAction {
        void onChange(int selection);
    }
}
