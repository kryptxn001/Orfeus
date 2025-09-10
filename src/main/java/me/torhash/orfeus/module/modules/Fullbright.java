package me.torhash.orfeus.module.modules;

import me.torhash.orfeus.module.Category;
import me.torhash.orfeus.module.Module;
import org.lwjgl.glfw.GLFW;

public class Fullbright extends Module {
    public Fullbright() {
        super("Fullbright", GLFW.GLFW_KEY_N, Category.RENDER);
    }

    @Override
    public void onEnable() {
        mc.options.gamma = 999;

        super.onEnable();
    }

    @Override
    public void onDisable() {
        mc.options.gamma = 1;
        super.onDisable();
    }
}
