package com.mifa.cloud.voice.server.commons.enums;
public enum LOGTYPE {
    OTHER("-1"), EMAIL("0"), DUBBO("1"), MQ("2");

    private final String value;

    LOGTYPE(String value) {
        this.value = value;
    }

    public String value() {
        return this.value;
    }
}