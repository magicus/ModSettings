package se.icus.mag.modsettings.gui;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.Selectable;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.ElementListWidget;
import net.minecraft.text.Text;

import java.util.List;

public class ModListWidget extends ElementListWidget<ModListWidget.Entry> {
    private static final int BUTTON_WIDTH = 150;
    private static final int BUTTON_GAP = 10;
    private static final int BUTTON_HEIGHT = 20;

    public ModListWidget(MinecraftClient client, int width, int height, int y, int itemHeight) {
        super(client, width, height, y, itemHeight);
        this.centerListVertically = false;
    }

    @Override
    public int getRowWidth() {
        return BUTTON_WIDTH * 2 + BUTTON_GAP;
    }

    @Override
    protected int getScrollbarX() {
        return super.getScrollbarX() + 32;
    }

    public void addAll(ModSettingsScreen.ModSettingsOption[] options) {
        for (int i = 0; i < options.length; i += 2) {
            addEntry(new ModEntry(options[i], i < options.length - 1 ? options[i + 1] : null));
        }
    }

    public class ModEntry extends Entry {
        private final ButtonWidget leftButton;
        private final ButtonWidget rightButton;
        private final List<ButtonWidget> buttons;

        public ModEntry(ModSettingsScreen.ModSettingsOption mod1, ModSettingsScreen.ModSettingsOption mod2) {
            this.leftButton = createButton(mod1);
            if (mod2 == null) {
                this.rightButton = null;
                this.buttons = ImmutableList.of(this.leftButton);
            } else {
                this.rightButton = createButton(mod2);
                this.buttons = ImmutableList.of(this.leftButton, this.rightButton);
            }
        }

        private ButtonWidget createButton(ModSettingsScreen.ModSettingsOption option) {
            return new Button(0, 0, BUTTON_WIDTH, BUTTON_HEIGHT, Text.of(option.modName()),
                    button -> ModListWidget.this.client.setScreen(option.configScreen()));
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
        public void render(DrawContext context, int mouseX, int mouseY, boolean hovered, float tickDelta) {
            int buttonY = getY() + (getHeight() - BUTTON_HEIGHT) / 2;
            this.leftButton.setPosition(getX(), buttonY);
            this.leftButton.render(context, mouseX, mouseY, tickDelta);

            if (this.rightButton != null) {
                this.rightButton.setPosition(getX() + BUTTON_WIDTH + BUTTON_GAP, buttonY);
                this.rightButton.render(context, mouseX, mouseY, tickDelta);
            }
        }
    }

    public static abstract class Entry extends ElementListWidget.Entry<Entry> {
    }
}
