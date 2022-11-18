package br.dh.meli.integratorprojectfresh.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * The enum ExceptionType lists the types and descriptions of custom exceptions.
 */
@Getter
@AllArgsConstructor
public enum ExceptionType {
  /**
   * A business rule was violated.
   */
  BUSINESS_RULE_EXCEPTION("Business rule violation"),
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
  PURCHASE_ORDER_NOT_FOUND("Purchase Order not found"),
  /**
   * An unknown error occurred.
   */
  UNKNOWN_SERVER_ERROR("An unknown server error ocurred.");

  private final String message;
}