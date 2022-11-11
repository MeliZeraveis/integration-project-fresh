package br.dh.meli.integratorprojectfresh.enums;

/**
 * The enum ExceptionType lists the types and descriptions of custom exceptions.
 */
public enum ExceptionType {
  /**
   * The Object was not found.
   */
  OBJECT_NOT_FOUND("Object not found"),
  /**
   * The Parameter is not valid.
   */
  PARAMETER_NOT_VALID("Parameter not valid"),

  /**
   * The Purchase Order was not found.
   */
  PURCHASE_ORDER_NOT_FOUND("Purchase Order not found");

   ExceptionType(String message) {
  }
}