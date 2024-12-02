package org.example.ebankify.util;

public class ResponseFormeWithToken<T> extends ResponseForme {
    String token;
    public ResponseFormeWithToken(String token,T data, String message) {
        super(data,message);
        this.token = token;
    }
    public String getToken() {
        return token;
    }
}
