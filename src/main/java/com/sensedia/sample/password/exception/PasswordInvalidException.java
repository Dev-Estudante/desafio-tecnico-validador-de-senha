package com.sensedia.sample.password.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class PasswordInvalidException extends RuntimeException {

    private String message;

    public PasswordInvalidException(String message) {
        super(message); // chama o construtor da RuntimeException
        this.message = message;
    }


}

