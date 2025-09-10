package me.torhash.orfeus.ui;

import me.torhash.orfeus.Orfeus;
import me.torhash.orfeus.event.EventTarget;
import me.torhash.orfeus.event.events.EventRender2D;
import me.torhash.orfeus.ui.uielements.ArrayList;
import me.torhash.orfeus.ui.uielements.MainHUD;
import me.torhash.orfeus.ui.uielements.tabgui.TabGUI;
import net.minecraft.client.gui.screen.Screen;


import java.util.concurrent.CopyOnWriteArrayList;

public class UIManager {
    private CopyOnWriteArrayList<UIElement> uielements = new CopyOnWriteArrayList();

    public UIManager() { Orfeus.Instance.eventManager.register(this); }

    public void loadUI() {
        addUIElements(new MainHUD());
        addUIElements(new ArrayList());
        addUIElements(new TabGUI());
    }

    @EventTarget
    public void onRender(EventRender2D e) {
        for(UIElement u : uielements) {
            if(u.isToggled()) {
                u.onRender(e.getMatrixStack());
            }
        }
    }

    public void addUIElements(UIElement u) {
        uielements.add(u);
    }

    public CopyOnWriteArrayList<UIElement> getUIElements() {
        return uielements;
    }

    public UIElement getUIElements(String name) {
        for (UIElement u : uielements) {
            if(u.getName().equalsIgnoreCase(name)) {
                return u;
            }
        }
        return null;
    }
}
