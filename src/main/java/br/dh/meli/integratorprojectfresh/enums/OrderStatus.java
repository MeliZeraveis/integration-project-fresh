package br.dh.meli.integratorprojectfresh.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;


/**
 * The enum OrderStatus lists the possible statuses of a purchase order.
 */
@Getter
@AllArgsConstructor
public enum OrderStatus {
  PENDING("Aberto"),
  APPROVED("Finalizado");

  public final String name;
}
