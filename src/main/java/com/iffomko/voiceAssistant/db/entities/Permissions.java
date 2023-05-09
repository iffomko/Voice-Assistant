package com.iffomko.voiceAssistant.db.entities;

public enum Permissions {
    GET_ANSWER("get:answer");

    private final String permission;

    Permissions(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
