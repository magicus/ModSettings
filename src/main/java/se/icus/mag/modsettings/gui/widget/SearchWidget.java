package se.icus.mag.modsettings.gui.widget;

import java.util.List;
import java.util.function.Consumer;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.ContainerWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class SearchWidget extends ContainerWidget {
    private static final int BUTTON_HEIGHT = 20;

    private final List<ClickableWidget> children;
    private final TextRenderer textRenderer;
    private final Runnable requestFocus;

    private final TextFieldWidget textBox;
    private boolean showTextBox;

    public SearchWidget(int x, int y, int width, String text, TextRenderer textRenderer,
                        Consumer<String> changedListener, Runnable requestFocus) {
        super(x, y, width, BUTTON_HEIGHT, Text.empty());

        this.textRenderer = textRenderer;
        this.requestFocus = requestFocus;

        IconButtonWidget searchButton = new IconButtonWidget(x, y, BUTTON_HEIGHT, BUTTON_HEIGHT,
                15, 15, new Identifier("modsettings", "search"),
                b -> {
                    this.setText("");

                    this.showTextBox = !this.showTextBox;
                    updateTextBoxVisibility();
                });
        searchButton.setTooltip(Tooltip.of(Text.translatable("modsettings.search.tooltip")));

        textBox = new TextFieldWidget(this.textRenderer, x + 26, y + 2, width - 26, 16,
                Text.empty());
        textBox.setText(text);
        textBox.setChangedListener(changedListener);

        this.showTextBox = !text.isEmpty();
        updateTextBoxVisibility();

        children = List.of(searchButton, textBox);
    }

    private void updateTextBoxVisibility() {
        if (this.showTextBox) {
            this.textBox.setVisible(true);
            this.textBox.setFocusUnlocked(false);
            this.textBox.setFocused(true);
            this.requestFocus.run();
        } else {
            this.textBox.setVisible(false);
            this.textBox.setFocusUnlocked(true);
            this.textBox.setFocused(false);
        }
    }

    @Override
    public void setFocused(Element focused) {
        // Force focus to textbox even if button was clicked
        super.setFocused(textBox);
    }

    @Override
    public List<? extends Element> children() {
        return children;
    }

    @Override
    protected void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
        children.forEach(c -> c.render(context, mouseX, mouseY, delta));
    }

    public String getText() {
        return textBox.getText();
    }

    public void setText(String filterText) {
        if (!filterText.equals(textBox.getText())) {
            textBox.setText(filterText);
        }
    }

    @Override
    protected void appendClickableNarrations(NarrationMessageBuilder builder) {
    }
}
