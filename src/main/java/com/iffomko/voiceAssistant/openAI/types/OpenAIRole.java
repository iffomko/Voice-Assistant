package com.iffomko.voiceAssistant.openAI.types;

/**
 * <p>Перечисление, которое хранит в себе все роли, которые могут быть у сообщения</p>
 */
public enum OpenAIRole {
    USER("User"),
    ASSISTANT("Assistant"),
    SYSTEM("System");

    private final String role;

    OpenAIRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
