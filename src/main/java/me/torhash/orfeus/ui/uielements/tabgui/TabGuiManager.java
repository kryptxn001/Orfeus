package me.torhash.orfeus.ui.uielements.tabgui;

import me.torhash.orfeus.Orfeus;
import me.torhash.orfeus.event.EventTarget;
import me.torhash.orfeus.module.Module;
import me.torhash.orfeus.ui.uielements.tabgui.categories.*;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

public class TabGuiManager {
    private static ArrayList<TabCategory> tabCategories = new ArrayList<>();

    public TabGuiManager() {
        tabCategories.add(new Combat());
        tabCategories.add(new Render());
        tabCategories.add(new Player());
        tabCategories.add(new Movement());
        tabCategories.add(new Misc());

        TabCategory tabCategory = tabCategories.get(tabNum);
        tabCategory.setSelected(true);
    }

    private static int moduleNum = 0;
    private static int tabNum = 0;
    public static boolean moduleSelected = false;

    public void ProccesKeys(int keyCode) {
        if(keyCode == GLFW.GLFW_KEY_DOWN) {
            if(moduleSelected) {
                for (TabCategory tabCategory : tabCategories) {
                    if(tabCategory.isOpened()) {
                        if (moduleNum != Orfeus.Instance.moduleManager.getModulesInCategory(tabCategory.getCategory()).size() - 1) {
                            moduleNum += 1;
                        } else {
                            moduleNum = 0;
                        }
                        for(Module m : Orfeus.Instance.moduleManager.getModulesInCategory(tabCategory.getCategory())) {
                            m.setSelected(false);
                        }

                        try {
                            Orfeus.Instance.moduleManager.getModulesInCategory(tabCategory.getCategory()).get(moduleNum).setSelected(true);
                        } catch (Exception e) { }
                        for(Module module : Orfeus.Instance.moduleManager.getModulesInCategory(tabCategory.getCategory())) {
                            if(module.isSelected()) {
                                System.out.println(module.getName());
                            }
                        }
                    }
                }
                return;
            }
            for(TabCategory tabC : tabCategories) {
                tabC.setSelected(false);
            }
            if (tabNum != tabCategories.size() - 1) {
                tabNum += 1;

            } else {
                tabNum = 0;
            }
            TabCategory tabCategory = tabCategories.get(tabNum);
            tabCategory.setSelected(true);
        }
        if(keyCode == GLFW.GLFW_KEY_UP) {
            if(moduleSelected) {
                for (TabCategory tabCategory : tabCategories) {
                    CopyOnWriteArrayList<Module> modulesinCat = Orfeus.Instance.moduleManager.getModulesInCategory(tabCategory.getCategory());

                    if(tabCategory.isOpened()) {
                        if (moduleNum != 0) {
                            moduleNum -= 1;
                        } else {
                            moduleNum = modulesinCat.size() - 1;
                        }

                        for(Module module : modulesinCat) {
                            module.setSelected(false);
                        }

                        try {
                            modulesinCat.get(moduleNum).setSelected(true);
                        } catch (Exception e) { }
                    }
                }
                return;
            }
            for(TabCategory tabC : tabCategories) {
                tabC.setSelected(false);
            }
            if (tabNum != 0) {
                tabNum -= 1;

            } else {
                tabNum = tabCategories.size() - 1;
            }
            TabCategory tabCategory = tabCategories.get(tabNum);
            tabCategory.setSelected(true);
        }
        if(keyCode == GLFW.GLFW_KEY_RIGHT) {
            if(!moduleSelected) {
                for(TabCategory tabCategory : tabCategories) {
                    tabCategory.setOpened(false);
                }

                TabCategory selectedCategory = tabCategories.get(tabNum);
                selectedCategory.setOpened(true);
                try {
                    Orfeus.Instance.moduleManager.getModulesInCategory(selectedCategory.getCategory()).get(0).setSelected(true);
                    moduleSelected = true;
                } catch (Exception e) {
                    System.out.println("No module in this category found!");
                }
            }
        }
        if(keyCode == GLFW.GLFW_KEY_LEFT) {
            for (TabCategory tabCategory : tabCategories) {
                tabCategory.setOpened(false);
                for(Module module : Orfeus.Instance.moduleManager.getModulesInCategory(tabCategory.getCategory())) {
                    module.setSelected(false);
                }
                moduleSelected = false;
                moduleNum = 0;
            }
        }
        if(keyCode == GLFW.GLFW_KEY_ENTER) {
            for (TabCategory tabCategory : tabCategories) {
                if(tabCategory.isSelected()) {
                    for (Module module : Orfeus.Instance.moduleManager.getModulesInCategory(tabCategory.getCategory())) {
                        if(module.isSelected()) {
                            module.toggle();
                        }
                    }
                }
            }
        }
    }

    public ArrayList<TabCategory> getCategories() {
        return tabCategories;
    }
}
