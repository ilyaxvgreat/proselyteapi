package com.khomchenko.proselyteapi.exceptions;

public class ProselyteapiException extends RuntimeException{
    public ProselyteapiException() {
    }

    public ProselyteapiException(String message) {
        super(message);
    }
}
