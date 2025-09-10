package me.torhash.orfeus.valuesystem;

public class Value<T> {
    T value;
    String name;
    String owner;

    public Value(String name, String owner,T value) {
        this.name = name;
        this.owner = owner;
        this.value = value;

        ValueManager.register(this);
    }

    public void setValue(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public String getOwner() {
        return owner;
    }
}
