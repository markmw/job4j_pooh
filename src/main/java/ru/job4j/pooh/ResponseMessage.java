package ru.job4j.pooh;

public enum ResponseMessage {
    GET("GET"),
    POST("POST"),
    OK("OK"),
    NOT_FOUND("204"),
    NOT_IMPL("501");

    private String value;

    ResponseMessage(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
