package me.torhash.orfeus.ui.uielements.tabgui;

import me.torhash.orfeus.Orfeus;
import me.torhash.orfeus.module.Module;
import me.torhash.orfeus.ui.UIElement;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;

public class TabGUI extends UIElement {
    public TabGUI() {
        super("TabGUI", true);
    }

    int background = 0x90000000;
    int textColor = -1;
    int selected = 0xFFffa600;
    int toggled = 0xFFc27e00;
    
    int textOffset = 2;
    int tabSizeY = textRenderer.fontHeight + textOffset * 2;
    int outline = 1;
    int tabSizeX = 60;
    int posX = 4;
    int posY = 24;

    @Override
    protected void onRender(MatrixStack matrixStack) {
        int count = 0;
        int modulecount = 0;

        Screen.fill(matrixStack,posX - outline, posY - outline, posX + outline + tabSizeX, posY + outline + tabSizeY * Orfeus.Instance.tabGuiManager.getCategories().size(), background);

        for(TabCategory tabCategory : Orfeus.Instance.tabGuiManager.getCategories()) {
            if(tabCategory.isSelected()) {
                Screen.fill(matrixStack,posX, posY + tabSizeY * count, posX + tabSizeX, posY + tabSizeY * count + tabSizeY, selected);
            }
            textRenderer.draw(matrixStack,tabCategory.getDisplayName(), posX + textOffset, posY + tabSizeY * count + textOffset, textColor);

            if(tabCategory.isOpened()) {
                if(Orfeus.Instance.moduleManager.getModulesInCategory(tabCategory.getCategory()).size() > 0) {
                    Screen.fill(matrixStack,posX + tabSizeX, posY - outline + tabSizeY * count, posX + outline * 2 + tabSizeX + tabSizeX, posY + outline + tabSizeY * Orfeus.Instance.moduleManager.getModulesInCategory(tabCategory.getCategory()).size() + tabSizeY * count, background);
                    Screen.fill(matrixStack,posX + tabSizeX, (posY - outline + tabSizeY * count) + 1, posX + tabSizeX + 1, (posY + outline + tabSizeY * Orfeus.Instance.moduleManager.getModulesInCategory(tabCategory.getCategory()).size() + tabSizeY * count) - 1, selected);
                    for(Module module : Orfeus.Instance.moduleManager.getModulesInCategory(tabCategory.getCategory())) {
                        if(module.isSelected()) {
                            Screen.fill(matrixStack,posX + tabSizeX + outline, posY + tabSizeY * (count + modulecount), posX + tabSizeX + tabSizeX + outline, posY + tabSizeY * (count + modulecount) + tabSizeY, selected);
                        } else if(module.isToggle()) {
                            Screen.fill(matrixStack,posX + tabSizeX + outline, posY + tabSizeY * (count + modulecount), posX + tabSizeX + tabSizeX + outline, posY + tabSizeY * (count + modulecount) + tabSizeY, toggled);
                        }
                        textRenderer.draw(matrixStack,module.getName(), posX + textOffset + tabSizeX + outline, posY + tabSizeY * (modulecount + count) + textOffset + 1, textColor);
                        modulecount++;
                    }
                }
            }

            count++;
        }
    }
}
