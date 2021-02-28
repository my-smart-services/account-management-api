package com.mss.accountmanagementapi.error;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.function.Supplier;

public class UnsupportedElementOperationException extends ResponseStatusException implements Supplier<RuntimeException> {

    public UnsupportedElementOperationException(String message) {
        super(HttpStatus.NOT_EXTENDED, message);
    }

    @Override
    public HttpHeaders getResponseHeaders() {
        HttpHeaders headers = new HttpHeaders();
        // headers.add(HttpHeaders.WARNING, "");
        return headers;
    }

    @Override
    public RuntimeException get() {
        return this;
    }
}
