package br.dh.meli.integratorprojectfresh.exception;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * The type Exceptions details validate.
 */
@Builder
@Data
public class ExceptionDetailsValidate {
  private String title;
  private String message;
  private String fields;
  private String fieldsMessages;
  private LocalDateTime timestamp;
}
