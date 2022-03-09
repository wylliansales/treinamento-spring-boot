package io.github.wyllian.rest.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import io.github.wyllian.exception.PedidoNaoEncontratoException;
import io.github.wyllian.exception.RegraNegocioException;
import io.github.wyllian.rest.ApiErrors;

@RestControllerAdvice
public class ApplicationControllerAdvice {
    
    @ExceptionHandler(RegraNegocioException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleRegraNegocioException(RegraNegocioException ex) {
        String msg = ex.getMessage();
        return new ApiErrors(msg);
    }

    @ExceptionHandler(PedidoNaoEncontratoException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiErrors handlePedidoNotFoundException(PedidoNaoEncontratoException ex) {
        return new ApiErrors(ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleMethodNotValidException(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult()
                                .getAllErrors()
                                .stream()
                                .map(err -> err.getDefaultMessage())
                                .collect(Collectors.toList());
        return new ApiErrors(errors);
    
    }
}
