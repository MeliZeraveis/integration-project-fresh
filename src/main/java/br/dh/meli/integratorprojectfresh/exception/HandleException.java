package br.dh.meli.integratorprojectfresh.exception;

import br.dh.meli.integratorprojectfresh.enums.ExceptionType;
import br.dh.meli.integratorprojectfresh.enums.Msg;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * HandleException - custom exception handler.
 */
@ControllerAdvice
public class HandleException extends ResponseEntityExceptionHandler {
  /**
   * Handler for NotFoundException entities.
   *
   * @param ex the NotFoundException instance
   * @return the generated ResponseEntity with the exception details and the status code
   */
  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<ExceptionDetails> handlerNotFoundException(NotFoundException ex) {
    ExceptionDetails exceptionDetails = ExceptionDetails.builder()
            .title(ExceptionType.OBJECT_NOT_FOUND.name())
            .message(ex.getMessage())
            .status(HttpStatus.NOT_FOUND.value())
            .timeStamp(LocalDateTime.now())
            .build();

    return new ResponseEntity<>(exceptionDetails, HttpStatus.NOT_FOUND);
  }

  /**
   * Handler for InvalidParamException entity.
   *
   * @param ex the InvalidParamException instance
   * @return the generated ResponseEntity with the exception details and the status code
   */
  @ExceptionHandler (InvalidParamException.class)
  public ResponseEntity<ExceptionDetails> handlerInvalidParamException(InvalidParamException ex) {
    ExceptionDetails exceptionDetails = ExceptionDetails.builder()
            .title(ExceptionType.PARAMETER_NOT_VALID.name())
            .message(ex.getMessage())
            .status(HttpStatus.BAD_REQUEST.value())
            .timeStamp(LocalDateTime.now())
            .build();

    return new ResponseEntity<>(exceptionDetails, HttpStatus.BAD_REQUEST);
  }

  /**
   * Handler for MethodArgumentNotValidException entity.
   *
   * @param ex the MethodArgumentNotValidException instance
   * @return the generated ResponseEntity with the exception details and the status code
   */
  @Override
  public ResponseEntity<Object> handleMethodArgumentNotValid(
          MethodArgumentNotValidException ex,
          HttpHeaders httpHeaders,
          HttpStatus httpStatus,
          WebRequest webRequest) {

    List<FieldError> errors = ex.getBindingResult().getFieldErrors();
    ExceptionDetailsValidate exceptionDetails = ExceptionDetailsValidate.builder()
            .title(ExceptionType.PARAMETER_NOT_VALID.name())
            .message(Msg.FIELD_NOT_FOUND)
            .fields(errors.stream().map(FieldError::getField).collect(Collectors.joining(";")))
            .fieldsMessages(errors.stream().map(FieldError::getDefaultMessage).collect(Collectors.joining(";")))
            .timestamp(LocalDateTime.now())
            .build();

    return new ResponseEntity<>(exceptionDetails, httpStatus);
  }
}