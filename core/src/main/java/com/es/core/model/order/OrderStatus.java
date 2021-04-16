package com.es.core.model.order;

public enum OrderStatus {
    NEW("New"), DELIVERED("Delivered"), REJECTED("Rejected");

    private final String displayName;

    OrderStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
