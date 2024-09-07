package org.attomicron.item;

public final class ItemName {

    private String displayName;

    public ItemName(String displayName) {
        this.displayName = displayName;
    }

    public String getValue() {
        return displayName;
    }

    public void setValue(String displayName) {
        this.displayName = displayName;
    }

}