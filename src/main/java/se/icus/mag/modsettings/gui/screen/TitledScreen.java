package se.icus.mag.modsettings.gui.screen;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public class TitledScreen extends Screen {
    private static final int TITLE_COLOR = 0xffffff;
    protected final Screen previous;

    public TitledScreen(Text title, Screen previous) {
        super(title);
        this.previous = previous;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        context.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, 5, TITLE_COLOR);
    }

    @Override
    public void close() {
        this.client.setScreen(this.previous);
    }
}
