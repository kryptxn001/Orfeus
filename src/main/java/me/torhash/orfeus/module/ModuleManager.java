package me.torhash.orfeus.module;

import me.torhash.orfeus.Orfeus;
import me.torhash.orfeus.event.EventTarget;
import me.torhash.orfeus.event.events.EventKeyboard;
import me.torhash.orfeus.module.modules.*;
import me.torhash.orfeus.valuesystem.ValueManager;
import net.minecraft.client.MinecraftClient;
import org.lwjgl.glfw.GLFW;

import java.util.concurrent.CopyOnWriteArrayList;

public class ModuleManager {
    private CopyOnWriteArrayList<Module> modules = new CopyOnWriteArrayList();

    public ModuleManager() {
        Orfeus.Instance.eventManager.register(this);
    }

    public void loadModules() {
        addModule(new Fullbright());
        addModule(new Killaura());
        addModule(new ESP());
        addModule(new BlockESP());
        addModule(new Scaffold());
        addModule(new Criticals());
        addModule(new NoFall());
        addModule(new Velocity());
        addModule(new Fish());
        addModule(new Step());
        addModule(new AutoFish());
    }

    @EventTarget
    public void onKey(EventKeyboard e) {
        if(e.getAction() != GLFW.GLFW_PRESS) {
            return;
        }

        if(MinecraftClient.getInstance().currentScreen != null) {
            return;
        }

        if(e.getKeyCode() == GLFW.GLFW_KEY_LEFT_ALT) {
            ValueManager.getValue("downmode","scaffold").setValue(!(boolean) (ValueManager.getValue("downmode","scaffold").getValue()));
            return;
        }

        Orfeus.Instance.tabGuiManager.ProccesKeys(e.getKeyCode());

        for (Module m : modules) {
            if (m.getKeyCode() == e.getKeyCode()) {
                m.toggle();
            }
        }
    }

    public CopyOnWriteArrayList<Module> getEnabledModules() {
        CopyOnWriteArrayList<Module> catmodules = new CopyOnWriteArrayList();

        for(Module m : modules) {
            if (m.isToggle()) {
                catmodules.add(m);
            }
        }
        return catmodules;
    }

    public void addModule(Module m) {
        modules.add(m);
    }

    public CopyOnWriteArrayList<Module> getModules() {
        return modules;
    }

    public Module getModule(String name) {
        for (Module m : modules) {
            if(m.getName().equalsIgnoreCase(name)) {
                return m;
            }
        }
        return null;
    }

    public CopyOnWriteArrayList<Module> getModulesInCategory(Category category) {
        CopyOnWriteArrayList<Module> catmodules = new CopyOnWriteArrayList();

        for(Module m : modules) {
            if (m.getCategory().equals(category)) {
                catmodules.add(m);
            }
        }
        return catmodules;
    }
}
