package se.icus.mag.modsettings.gui.widget;

import com.google.common.collect.ImmutableList;
import java.util.List;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.Selectable;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.ElementListWidget;
import net.minecraft.text.Text;
import se.icus.mag.modsettings.gui.ModConfigInfo;

public class ModListWidget extends ElementListWidget<ModListWidget.Entry> {
    private static final int BUTTON_HEIGHT = 20;

    public ModListWidget(MinecraftClient client, int width, int height, int y, int itemHeight) {
        super(client, width, height, y, itemHeight);
        this.centerListVertically = false;
    }

    @Override
    public int getRowWidth() {
        return 400;
    }

    @Override
    protected int getScrollbarPositionX() {
        return super.getScrollbarPositionX() + 32;
    }

    public void setModButtons(List<ModConfigInfo> options) {
        clearEntries();
        for (int i = 0; i < options.size(); i += 2) {
            addEntry(new ModEntry(options.get(i), i < options.size() - 1 ? options.get(i + 1) : null));
        }
    }

    public class ModEntry extends Entry {
        final List<ButtonWidget> buttons;

        public ModEntry(ModConfigInfo mod1, ModConfigInfo mod2) {
            ButtonWidget leftButton = new Button(ModListWidget.this.width / 2 - 155, 0, 150, BUTTON_HEIGHT, Text.of(mod1.modName()),
                    button -> client.setScreen(mod1.configScreen()));
            if (mod2 != null) {
                ButtonWidget rightButton = new Button(ModListWidget.this.width / 2 - 155 + 160, 0, 150, BUTTON_HEIGHT, Text.of(mod2.modName()),
                        button -> client.setScreen(mod2.configScreen()));
                buttons = ImmutableList.of(leftButton, rightButton);
            } else {
                buttons = ImmutableList.of(leftButton);
            }
        }

        @Override
        public List<? extends Selectable> selectableChildren() {
            return buttons;
        }

        @Override
        public List<? extends Element> children() {
            return buttons;
        }

        @Override
        public void render(DrawContext context, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
            buttons.forEach(button -> {
                button.setY(y);
                button.render(context, mouseX, mouseY, tickDelta);
            });
        }
    }

    public abstract static class Entry extends ElementListWidget.Entry<Entry> {
    }
}
