package com.example.antrianpuskesmas.data.ref;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public enum QueueStatus {

    WAIT("WAITING"),
    DONE("DONE");

    private static final List<String> VALUES;

    private final String value;

    static {
        VALUES = new ArrayList<>();
        for (QueueStatus ut : QueueStatus.values()) {
            VALUES.add(ut.value);
        }
    }

    QueueStatus(String value) {
        this.value = value;
    }

    public static List<String> getValues() {
        return Collections.unmodifiableList(VALUES);
    }

    public String getValue() {
        return value;
    }

    public static QueueStatus fromString(String text) {
        for (QueueStatus u : QueueStatus.values()) {
            if (u.value.equals(text)) {
                return u;
            }
        }
        return null;
    }
}
