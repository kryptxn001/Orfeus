package me.torhash.orfeus.event.events;

import me.torhash.orfeus.event.Event;

public class EventMouseClick extends Event{
    private clickType clickType;

    public EventMouseClick(clickType clickType) {
        this.clickType = clickType;
    }

    public enum clickType {
        LEFT,RIGHT
    }

    public EventMouseClick.clickType getClickType() {
        return clickType;
    }


}
