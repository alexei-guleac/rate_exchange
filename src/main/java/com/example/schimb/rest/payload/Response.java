package com.example.schimb.rest.payload;

public class Response<T> {
    private T value;

    public Response(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }
}
