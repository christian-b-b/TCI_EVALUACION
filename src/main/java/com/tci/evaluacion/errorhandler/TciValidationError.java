package com.tci.evaluacion.errorhandler;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class TciValidationError extends TciSubError{
  private String object;
  private String field;
  private Object rejectValue;
  private String message;

}
