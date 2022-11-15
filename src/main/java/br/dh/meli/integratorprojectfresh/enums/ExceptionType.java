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

  private final String message;
}