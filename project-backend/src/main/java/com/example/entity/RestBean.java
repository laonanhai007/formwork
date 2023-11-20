package com.example.entity;

import lombok.Data;

@Data
public class RestBean<T> {
    private int statusCode;
    private boolean success;
    private T message;

    private RestBean(int statusCode, boolean success, T message) {
        this.statusCode = statusCode;
        this.success = success;
        this.message = message;
    }

    public static <T> RestBean<T> success() {
        return new RestBean<>(200, true, null);
    }

    public static <T> RestBean<T> success(T data) {
        return new RestBean<>(200, true, data);
    }

    public static <T> RestBean<T> failure(int statusCode) {
        return new RestBean<>(statusCode, false, null);
    }

    public static <T> RestBean<T> failure(int statusCode, T data) {
        return new RestBean<>(statusCode, false, data);
    }
}
