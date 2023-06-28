package com.tci.evaluacion.security;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.tci.evaluacion.constants.TciConstants;
import com.tci.evaluacion.errorhandler.TciError;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

public class TciAccessDeniedHandler implements AccessDeniedHandler {

  @Override
  public void handle(HttpServletRequest request, HttpServletResponse response,
      AccessDeniedException accessDeniedException) throws IOException {
    TciError tciError = TciError.builder()
        .httpStatus(HttpStatus.FORBIDDEN)
        .message("JWT is not valid")
        .code(TciConstants.PREFIX_CLIENT_ERROR + TciConstants.FORBIDDEN)
        .build();
    response.setContentType("application/json");
    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
    OutputStream outputStream = response.getOutputStream();
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.registerModule(new JavaTimeModule());
    objectMapper.writeValue(outputStream, tciError);
    outputStream.flush();
  }

}
