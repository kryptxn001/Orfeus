package me.torhash.orfeus;

import me.torhash.orfeus.event.EventManager;
import me.torhash.orfeus.module.ModuleManager;
import me.torhash.orfeus.ui.UIManager;
import me.torhash.orfeus.ui.uielements.tabgui.TabGuiManager;
import me.torhash.orfeus.util.ColorUtils;
import net.minecraft.client.util.Session;

public class Orfeus {
    public String name = "Orfeus", version = "1.0", author = "torhash";

    public EventManager eventManager;
    public ColorUtils colorUtils = new ColorUtils(5);
    public TabGuiManager tabGuiManager;
    public ModuleManager moduleManager;
    public UIManager uiManager;
    public Session orfeussession = null;

    public static Orfeus Instance = new Orfeus();

    public void start() {
        tabGuiManager = new TabGuiManager();
        eventManager = new EventManager();
        moduleManager = new ModuleManager();
        uiManager = new UIManager();

        uiManager.loadUI();
        moduleManager.loadModules();
    }
}
