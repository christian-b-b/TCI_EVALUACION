package com.tci.evaluacion.errorhandler;


import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
public class TciGenericServerException extends  RuntimeException {

  private String code;
  private HttpStatus httpStatus;

  public TciGenericServerException(String message, HttpStatus httpStatus){
    super(message);
    this.httpStatus = httpStatus;
  }

  public TciGenericServerException(String message) {
    super(message);
  }
}
