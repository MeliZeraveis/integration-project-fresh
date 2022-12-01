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
   * handleMethodArgumentNotValid - handle the validation errors.
   * @param ex - the exception
   * @return ResponseEntity<ExceptionResponseDTO> - the exception response
   */
  @ExceptionHandler(BusinessRuleException.class)
  public ResponseEntity<ExceptionDetails> handlerBusinessRuleException(BusinessRuleException ex) {
    ExceptionDetails exceptionDetails = ExceptionDetails.builder()
            .title(ExceptionType.BUSINESS_RULE_EXCEPTION.name())
            .message(ex.getMessage())
            .status(HttpStatus.UNPROCESSABLE_ENTITY.value())
            .timeStamp(LocalDateTime.now())
            .build();

    return new ResponseEntity<>(exceptionDetails, HttpStatus.UNPROCESSABLE_ENTITY);
  }

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


  @ExceptionHandler (ActionNotAllowedException.class)
  public ResponseEntity<ExceptionDetails> handlerLimitCapacitySectionException(ActionNotAllowedException ex) {
    ExceptionDetails exceptionDetails = ExceptionDetails.builder()
            .title("Action not allowed")
            .message(ex.getMessage())
            .status(HttpStatus.UNPROCESSABLE_ENTITY.value())
            .timeStamp(LocalDateTime.now())
            .build();

    return new ResponseEntity<>(exceptionDetails, HttpStatus.UNPROCESSABLE_ENTITY);
  }

  @ExceptionHandler (SectionTypeException.class)
  public ResponseEntity<ExceptionDetails> handlerSectionTypeException(SectionTypeException ex) {
    ExceptionDetails exceptionDetails = ExceptionDetails.builder()
            .title("Section Type incorrect")
            .message(ex.getMessage())
            .status(HttpStatus.UNPROCESSABLE_ENTITY.value())
            .timeStamp(LocalDateTime.now())
            .build();

    return new ResponseEntity<>(exceptionDetails, HttpStatus.UNPROCESSABLE_ENTITY);
  }

  @ExceptionHandler (ManagerNotValidException.class)
  public ResponseEntity<ExceptionDetails> handlerManagerNotValidException(ManagerNotValidException ex) {
    ExceptionDetails exceptionDetails = ExceptionDetails.builder()
            .title("Manager not valid")
            .message(ex.getMessage())
            .status(HttpStatus.UNPROCESSABLE_ENTITY.value())
            .timeStamp(LocalDateTime.now())
            .build();

    return new ResponseEntity<>(exceptionDetails, HttpStatus.UNPROCESSABLE_ENTITY);
  }

  /**
   * Handler for MethodArgumentNotValidException entity.
   *
   * @param ex the MethodArgumentNotValidException instance
   * @return the generated ResponseEntity with the exception details and the status code
   */
  @ExceptionHandler (MethodArgumentNotValidException.class)
  public ResponseEntity<Object> handleMethodArgumentNotValid(
          MethodArgumentNotValidException ex,
          HttpHeaders _httpHeaders,
          HttpStatus httpStatus,
          WebRequest _webRequest) {

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

  /**
   * Handler for general unknown exception entities.
   * @param ex the Exception instance
   * @return ResponseEntity - the generated ResponseEntity with the exception details and the status code
   */
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ExceptionDetails> handlerGeneralErrorException(Exception ex) {
    ExceptionDetails exceptionDetails = ExceptionDetails.builder()
            .title(ExceptionType.UNKNOWN_SERVER_ERROR.name())
            .message(ex.getMessage())
            .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
            .timeStamp(LocalDateTime.now())
            .build();

    return new ResponseEntity<>(exceptionDetails, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}