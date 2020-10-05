package com.elux.appliancecontrol.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ApplianceNotFoundException extends Exception {

    public ApplianceNotFoundException(String message) {
        super(message);
    }

    public ApplianceNotFoundException(String message, Throwable t) {
        super(message, t);
    }
}
