package com.mss.accountmanagementapi.common.error;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.function.Supplier;

public class ElementAlreadyPresentException extends ResponseStatusException implements Supplier<RuntimeException> {

    public ElementAlreadyPresentException(String message) {
        super(HttpStatus.UNPROCESSABLE_ENTITY, message);
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
