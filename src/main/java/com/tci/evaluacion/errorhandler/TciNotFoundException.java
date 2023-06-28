package com.tci.evaluacion.errorhandler;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class TciNotFoundException extends RuntimeException {
    private String code;
}
