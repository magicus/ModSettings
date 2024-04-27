package se.icus.mag.modsettings.gui.widget;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class IconButtonWidget extends ButtonWidget {
    private final int textureWidth;
    private final int textureHeight;
    protected Identifier texture;

    public IconButtonWidget(int x, int y, int width, int height,int textureWidth, int textureHeight,
                            Identifier texture, ButtonWidget.PressAction onPress) {
        this(x, y, width, height, textureWidth, textureHeight, onPress);
        this.texture = texture;
    }

    protected IconButtonWidget(int x, int y, int width, int height,int textureWidth, int textureHeight,
                            ButtonWidget.PressAction onPress) {
        super(x, y, width, height, Text.empty(), onPress, DEFAULT_NARRATION_SUPPLIER);
        this.textureWidth = textureWidth;
        this.textureHeight = textureHeight;
    }

    @Override
    public void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
        super.renderWidget(context, mouseX, mouseY, delta);
        int x = this.getX() + this.getWidth() / 2 - this.textureWidth / 2;
        int y = this.getY() + this.getHeight() / 2 - this.textureHeight / 2;
        context.drawGuiTexture(this.texture, x, y, this.textureWidth, this.textureHeight);
    }

    @Override
    public void drawMessage(DrawContext context, TextRenderer textRenderer, int color) {
    }
}
