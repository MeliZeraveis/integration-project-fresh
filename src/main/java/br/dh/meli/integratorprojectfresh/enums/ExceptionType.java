package br.dh.meli.integratorprojectfresh.enums;

/**
 * The enum ExceptionType lists the types and descriptions of custom exceptions.
 */
public enum ExceptionType {
  /**
   * The Object not found.
   */
  OBJECT_NOT_FOUND("Object not found"),
  /**
   * The Parameter not valid.
   */
  PARAMETER_NOT_VALID("Parameter not valid");


  /**
   * The message public attribute.
   */
  public final String message;

  ExceptionType(String message) {
    this.message = message;
  }
}