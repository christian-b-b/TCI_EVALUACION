package com.tci.evaluacion.errorhandler;

import com.tci.evaluacion.constants.TciConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@ControllerAdvice
public class TciExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(HttpStatusCodeException.class)
  protected ResponseEntity<Object> handleHttpRestClient(HttpStatusCodeException ex) {
    TciError tciError = null;
    if (ex.getStatusCode().is4xxClientError()) {
      tciError = TciError.builder().httpStatus(ex.getStatusCode())
          .code(TciConstants.PREFIX_CLIENT_ERROR).build();
    } else if (ex.getStatusCode().is5xxServerError()) {
      tciError = TciError.builder().httpStatus(ex.getStatusCode())
          .code(TciConstants.PREFIX_SERVER_ERROR).build();
    }
    tciError.setMessage(ex.getMessage());
    log.error("Error HTTP Request Client: {}", ex.getMessage());
    return buildResponseEntity(tciError);
  }

  @ExceptionHandler(TciNotFoundException.class)
  protected ResponseEntity<Object> handleEntityNotFound(TciNotFoundException ex) {
    log.error("Entity Not Found: {}", ex.getMessage());
    if (ex.getCode()!=null){
      return buildResponseEntity(TciError.builder().httpStatus(HttpStatus.NOT_FOUND)
              .code(ex.getCode())
              .message(ex.getMessage()).build());

    }else{
      return buildResponseEntity(TciError.builder().httpStatus(HttpStatus.NOT_FOUND)
              .code(TciConstants.PREFIX_CLIENT_ERROR + TciConstants.NOT_FOUND)
              .message(ex.getMessage()).build());
    }

  }

  @ExceptionHandler(TciUnauthorizedException.class)
  protected ResponseEntity<Object> handleUnauthorized(TciUnauthorizedException ex) {
    log.error("Unauthorized: {}", ex.getMessage());
    if (ex.getCode() == null){
      TciError tciError = TciError.builder().httpStatus(HttpStatus.UNAUTHORIZED)
              .code(TciConstants.PREFIX_CLIENT_ERROR + TciConstants.UNAUTHORIZED)
              .message(ex.getMessage()).build();
      return buildResponseEntity(tciError);
    }else{
      TciError tciError = TciError.builder().httpStatus(ex.getHttpStatus())
              .code(ex.getCode())
              .message(ex.getMessage()).build();
      return buildResponseEntity(tciError);
    }
  }

  @ExceptionHandler(TciGenericServerException.class)
  protected ResponseEntity<Object> handleGenericServerError(TciGenericServerException ex) {
    TciError tciError = null;
    if (ex.getCode() != null) {
      tciError = TciError.builder().httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
          .code(ex.getCode()).build();
    } else {
      tciError = TciError.builder().httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
          .code(TciConstants.PREFIX_SERVER_ERROR + TciConstants.INTERNAL_SERVER_ERROR)
          .build();
    }
    tciError.setMessage(ex.getMessage());
    log.error("Internal Server Error: {}", ex.getMessage());
    return buildResponseEntity(tciError);
  }

  @ExceptionHandler(TciGenericClientException.class)
  protected ResponseEntity<Object> handleGenericClientException(TciGenericClientException ex) {
    log.error("Client Error: {}", ex.getMessage());
    return buildResponseEntity(TciError.builder().httpStatus(ex.getHttpStatus())
        .code(TciConstants.PREFIX_CLIENT_ERROR).message(ex.getMessage())
        .subErrors(ex.getSubErrors()).build());
  }
  @ExceptionHandler(Exception.class)
  protected ResponseEntity<Object> handleError(Exception ex) {
    log.error("Server Error: {}", ex.getMessage());
    return buildResponseEntity(TciError.builder()
        .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
        .code(TciConstants.PREFIX_SERVER_ERROR + TciConstants.INTERNAL_SERVER_ERROR)
        .message(ex.getMessage()).build());
  }

  private ResponseEntity<Object> buildResponseEntity(TciError tciError) {
    return new ResponseEntity<>(tciError, tciError.getHttpStatus());
  }

  protected List<TciSubError> fillValidationErrorsFrom(
      MethodArgumentNotValidException argumentNotValid) {
    List<TciSubError> subErrorCollection = new ArrayList<>();
    argumentNotValid.getBindingResult().getFieldErrors().get(0).getRejectedValue();
    argumentNotValid.getBindingResult().getFieldErrors().stream().forEach((objError) -> {
      subErrorCollection.add(TciValidationError.builder().object(objError.getObjectName())
          .field(objError.getField()).rejectValue(objError.getRejectedValue())
          .message(objError.getDefaultMessage()).build());
    });
    return subErrorCollection;
  }

}
