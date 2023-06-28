package com.tci.evaluacion.errorhandler;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class TciUnauthorizedException extends RuntimeException {
    private String code;
    private HttpStatus httpStatus;
    public TciUnauthorizedException(String code, String message, HttpStatus httpStatus) {
        super(message);
        this.code = code;
        this.httpStatus = httpStatus;
    }
}
