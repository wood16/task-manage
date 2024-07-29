package com.example.taskmanage.common.constant;

public enum TaskStatus implements Value {

    PENDING("pending"),
    PROCESSING("processing"),
    COMPLETE("complete"),
    CANCEL("cancel"),
    PAUSE("pause");

    private final String value;

    TaskStatus(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return value;
    }
}
