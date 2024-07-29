package com.example.taskmanage.common.constant;

public enum HistoryAction implements Value{

    CREATE("create"),
    UPDATE("update"),
    DELETE("delete");

    private final String value;

    HistoryAction(String value) {
        this.value = value;
    }


    @Override
    public String getValue() {
        return value;
    }
}
