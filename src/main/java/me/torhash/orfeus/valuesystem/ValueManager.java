package me.torhash.orfeus.valuesystem;

import java.util.ArrayList;

public class ValueManager {
    private static ArrayList<Value> values = new ArrayList<>();;

    public static void register(Value value) {
        values.add(value);
    }

    public static Value getValue(String name, String owner) {
        for(Value value : values) {
            if(value.getName().equalsIgnoreCase(name) && value.getOwner().equalsIgnoreCase(owner)) {
                return value;
            }
        }
        return null;
    }
}
