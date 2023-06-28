package com.tci.evaluacion.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.tci.evaluacion.constants.TciConstants;
import com.tci.evaluacion.errorhandler.TciError;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

public class TciAuthenticationEntryPoint implements AuthenticationEntryPoint {

  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response,
      AuthenticationException authException) throws IOException {
    TciError tciError = TciError.builder()
        .httpStatus(HttpStatus.UNAUTHORIZED)
        .message("Expired or invalid JWT token")
        .code(TciConstants.PREFIX_CLIENT_ERROR + TciConstants.UNAUTHORIZED)
        .build();
    response.setContentType("application/json");
    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    OutputStream outputStream = response.getOutputStream();
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.registerModule(new JavaTimeModule());
    objectMapper.writeValue(outputStream, tciError);
    outputStream.flush();
  }

}
