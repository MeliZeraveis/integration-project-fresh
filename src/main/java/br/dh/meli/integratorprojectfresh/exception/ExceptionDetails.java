package br.dh.meli.integratorprojectfresh.exception;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * The type Exception details.
 */
@Data
@Builder
public class ExceptionDetails {
  private String title;
  private int status;
  private String message;
  private LocalDateTime timeStamp;
}