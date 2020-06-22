package com.vgowda.hasura.chinook;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseExceptionHandler extends ResponseEntityExceptionHandler {
  @ExceptionHandler(value = {
      ResponseStatusException.class
  })
  protected ResponseEntity<Object> handleError(
      ResponseStatusException ex, WebRequest request) {
    Map<String, Object> body = new HashMap<>();
    body.put("reason", ex.getReason());
    body.put("timestamp", ZonedDateTime.now());
    return handleExceptionInternal(ex, body, ex.getResponseHeaders(), ex.getStatus(), request);
  }
}
