package me.torhash.orfeus.ui.uielements;

import me.torhash.orfeus.Orfeus;
import me.torhash.orfeus.module.Module;
import me.torhash.orfeus.ui.UIElement;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ArrayList extends UIElement {
    public ArrayList() {
        super("ArrayList", true);
    }

    private class ModuleComparator implements Comparator<Module> {
        @Override
        public int compare(Module arg0, Module arg1) {
            if(mc.textRenderer.getWidth(arg0.getName()) > mc.textRenderer.getWidth(arg1.getName())) {
                return -1;
            }
            if(mc.textRenderer.getWidth(arg0.getName()) <mc.textRenderer.getWidth(arg1.getName())) {
                return 1;
            }
            return 0;
        }
    }

    @Override
    protected void onRender(MatrixStack matrixStack) {
        int count = 0;

        List<Module> modules = Orfeus.Instance.moduleManager.getEnabledModules();

        Collections.sort(modules, new ModuleComparator());

        for(Module m : modules) {
            double offset = (textRenderer.fontHeight + 2) * count;

            Screen.fill(matrixStack,mc.getWindow().getScaledWidth() - mc.textRenderer.getWidth(m.getName()) - 4, (int) Math.floor(offset)  , mc.getWindow().getScaledWidth(), textRenderer.fontHeight + (int) Math.floor(offset) + 2, 0x90000000);
            Screen.fill(matrixStack,mc.getWindow().getScaledWidth() - mc.textRenderer.getWidth(m.getName()) - 4 - 2, (int) Math.floor(offset)  , mc.getWindow().getScaledWidth() - mc.textRenderer.getWidth(m.getName()) - 4, textRenderer.fontHeight + (int) Math.floor(offset) + 2, 0xFFffa600);
            textRenderer.drawWithShadow(matrixStack,m.getName(), mc.getWindow().getScaledWidth() - mc.textRenderer.getWidth(m.getName()) - 2, (float)offset + 1, 0xFFffa600);
            count++;
        }
    }
}
