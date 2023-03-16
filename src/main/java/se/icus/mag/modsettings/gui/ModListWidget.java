package se.icus.mag.modsettings.gui;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.Selectable;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.ElementListWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

import java.util.List;

public class ModListWidget extends ElementListWidget<ModListWidget.Entry> {
    private static final int BUTTON_HEIGHT = 20;

    public ModListWidget(MinecraftClient client, int width, int height, int top, int bottom, int itemHeight) {
        super(client, width, height, top, bottom, itemHeight);
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

    public void addAll(ModSettingsScreen.ModSettingsOption[] options) {
        for (int i = 0; i < options.length; i += 2) {
            addEntry(new ModEntry(options[i], i < options.length - 1 ? options[i + 1] : null));
        }
    }

    public class ModEntry extends Entry {
        final List<ButtonWidget> buttons;

        public ModEntry(ModSettingsScreen.ModSettingsOption mod1, ModSettingsScreen.ModSettingsOption mod2) {
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
        public void render(MatrixStack matrices, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
            buttons.forEach(button -> {
                button.setY(y);
                button.render(matrices, mouseX, mouseY, tickDelta);
            });
        }
    }

    public static abstract class Entry extends ElementListWidget.Entry<Entry> {
    }
}
