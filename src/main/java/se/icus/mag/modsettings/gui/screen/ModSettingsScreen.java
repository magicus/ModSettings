package se.icus.mag.modsettings.gui.screen;

import java.util.LinkedList;
import java.util.List;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import se.icus.mag.modsettings.Main;
import se.icus.mag.modsettings.ModRegistry;
import se.icus.mag.modsettings.gui.ModConfigInfo;
import se.icus.mag.modsettings.gui.widget.Button;
import se.icus.mag.modsettings.gui.widget.IconToggleButtonWidget;
import se.icus.mag.modsettings.gui.widget.ModListWidget;
import se.icus.mag.modsettings.gui.widget.SearchWidget;

public class ModSettingsScreen extends TitledScreen {
    private static final int FULL_BUTTON_WIDTH = 200;
    private static final int BUTTON_HEIGHT = 20;

    private boolean initIsProcessing;
    private ModListWidget list;
    private SearchWidget searchWidget;

    public ModSettingsScreen(Screen previous) {
        super(Text.translatable("modsettings.screen.title"), previous);
    }

    @Override
    protected void init() {
        // Protect against mods like Content Creator Integration that triggers
        // a recursive call of Screen.init() while creating the settings screen...
        if (initIsProcessing) return;
        initIsProcessing = true;

        // Add the toggle show indirect mods button
        IconToggleButtonWidget showIndirectButton = new IconToggleButtonWidget(10, 6,
                BUTTON_HEIGHT, BUTTON_HEIGHT, 15, 15,
                List.of(new Identifier("modsettings", "expand"),
                        new Identifier("modsettings", "collapse")),
                List.of(Tooltip.of(Text.translatable("modsettings.indirect.show")),
                        Tooltip.of(Text.translatable("modsettings.indirect.hide"))),
                Main.OPTIONS.showIndirect ? 1 : 0, selection -> {
                    Main.OPTIONS.showIndirect = (selection == 1);
                    updateModButtons();
                });
        this.addDrawableChild(showIndirectButton);

        // Add the search widget
        searchWidget = new SearchWidget(40, 6, 100,
                Main.OPTIONS.filterText, this.textRenderer, text -> {
                    Main.OPTIONS.filterText = text;
                    updateModButtons();
                }, () -> this.setFocused(searchWidget));

        this.addDrawableChild(searchWidget);
        this.setInitialFocus(searchWidget);

        // Add the actual mod list buttons
        // Put the list between 32 pixels from top and bottom
        this.list = new ModListWidget(this.client, this.width, this.height - 64, 32, 25);

        this.addDrawableChild(this.list);

        // Add the Done button
        this.addDrawableChild(new Button(this.width / 2 - FULL_BUTTON_WIDTH / 2, this.height - 27, FULL_BUTTON_WIDTH, BUTTON_HEIGHT, ScreenTexts.DONE, button -> this.client.setScreen(this.previous)));

        updateModButtons();
        initIsProcessing = false;
    }

    private void updateModButtons() {
        List<String> visibleModIds = ModRegistry.getInstance().getVisibleModIds(Main.OPTIONS.showIndirect, Main.OPTIONS.filterText);
        this.list.setModButtons(getModConfigInfo(visibleModIds));
    }

    private List<ModConfigInfo> getModConfigInfo(List<String> modIds) {
        List<ModConfigInfo> options = new LinkedList<>();
        for (String modId : modIds) {
            try {
                Screen configScreen = ModRegistry.getInstance().getConfigScreen(modId, this);
                if (configScreen != null) {
                    options.add(new ModConfigInfo(modId, ModRegistry.getInstance().getModName(modId), configScreen));
                }
            } catch (Throwable e) {
                Main.LOGGER.error("Error creating Settings screen from mod " + modId, e);
            }
        }
        return options;
    }
}
