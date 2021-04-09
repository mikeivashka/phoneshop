package com.es.core.enumeration;

public enum SortField {
    BRAND("brand"),
    MODEL("model"),
    DISPLAY_SIZE("displaySizeInches"),
    PRICE("price");

    private final String columnName;

    SortField(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnName() {
        return columnName;
    }
}
