package me.torhash.orfeus.event;

import java.lang.reflect.Method;

public class Data {

    private Object source;
    private Method target;
    private me.torhash.orfeus.event.EventPriority priority;

    public Data(Object source, Method target, me.torhash.orfeus.event.EventPriority priority) {
        this.source = source;
        this.target = target;
        this.priority = priority;
    }

    public Object getSource() {
        return source;
    }

    public Method getTarget() {
        return target;
    }

    public me.torhash.orfeus.event.EventPriority getPriority() {
        return priority;
    }
}
