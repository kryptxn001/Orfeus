package me.torhash.orfeus.ui.uielements.tabgui;

import me.torhash.orfeus.module.Category;

public class TabCategory {
    private boolean isSelected = false;
    private boolean isOpened = false;
    private String displayName;
    private Category category;

    public TabCategory(String displayName, Category category) {
        this.displayName = displayName;
        this.category = category;
    }

    public String getDisplayName() {
        return displayName;
    }

    public Category getCategory() {
        return category;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public void setOpened(boolean opened) {
        isOpened = opened;
    }

    public Boolean isSelected() {
        return isSelected;
    }

    public boolean isOpened() {
        return isOpened;
    }

}
