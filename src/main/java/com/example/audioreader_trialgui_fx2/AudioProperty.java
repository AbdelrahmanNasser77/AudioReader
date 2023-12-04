package com.example.audioreader_trialgui_fx2;


public class AudioProperty {
    private String columnName;
    private String value;

    public AudioProperty(String columnName, String value) {
        this.columnName = columnName;
        this.value = value;
    }

    public AudioProperty(String columnName, int value) {
        this.columnName = columnName;
        this.value = String.valueOf(value);
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
