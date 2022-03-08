
package ru.demskv.webapplicationproject;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;


@Provider
public class MyExceptionMapper
               implements ExceptionMapper<ConstraintViolationException> {

  @Override
  public Response toResponse(final ConstraintViolationException exception) {
      return Response.status(Response.Status.BAD_REQUEST)
                     .entity(prepareMessage(exception))
                     .type("text/plain")
                     .build();
  }

  private String prepareMessage(ConstraintViolationException exception) {
      String msg = "";
      for (ConstraintViolation<?> cv : exception.getConstraintViolations()) {
          msg+=cv.getPropertyPath()+" "+cv.getMessage()+"\n";
      }
      return msg;
  }
}
