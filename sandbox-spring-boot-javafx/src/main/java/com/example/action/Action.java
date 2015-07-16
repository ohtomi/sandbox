package com.example.action;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Action {

    private final String type;

    private final Map<String, Object> payload;

    Action(String type) {
        this(type, new HashMap<>());
    }

    Action(String type, Map<String, Object> payload) {
        this.type = type;
        this.payload = payload;
    }

    public String getType() {
        return type;
    }

    public Map<String, Object> getPayload() {
        return Collections.unmodifiableMap(payload);
    }

    @SuppressWarnings("unchecked")
    public <T> T getPayloadEntry(String key) {
        return (T) payload.get(key);
    }

    public Action putPayloadEntry(String key, Object value) {
        if (key != null) {
            payload.put(key, value);
        }
        return this;
    }

}
