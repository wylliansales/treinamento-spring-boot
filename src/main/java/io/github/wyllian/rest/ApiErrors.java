package io.github.wyllian.rest;

import java.util.Arrays;
import java.util.List;

import lombok.Getter;


public class ApiErrors {
    
    @Getter
    private List<String> errors;

    public ApiErrors(String messageError) {
        this.errors = Arrays.asList(messageError);
    }

    public ApiErrors(List<String> errors) {
        this.errors = errors;
    }
}
