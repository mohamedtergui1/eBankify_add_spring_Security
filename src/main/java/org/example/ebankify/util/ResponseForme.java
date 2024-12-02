package org.example.ebankify.util;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseForme<T> {

    private T data;
    private String message;

    public ResponseForme(T data, String message) {
        this.data = data;
        this.message = message;
    }

    public ResponseForme(T data) {
        this.data = data;
    }

    public ResponseForme() {

    }

    public ResponseForme(String message) {
        this.message = message;
    }

}
