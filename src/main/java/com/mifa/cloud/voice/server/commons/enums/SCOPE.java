package com.mifa.cloud.voice.server.commons.enums;
public enum SCOPE {
    ALL, BEFORE, AFTER;

    public boolean contains(SCOPE scope) {
        if (this == ALL) {
            return true;
        } else {
            return this == scope;
        }
    }

    @Override
    public String toString() {
        String str = "";
        switch (this) {
        case ALL:
            break;
        case BEFORE:
            str = "REQUEST";
            break;
        case AFTER:
            str = "RESPONSE";
            break;
        }
        return str;
    }
}