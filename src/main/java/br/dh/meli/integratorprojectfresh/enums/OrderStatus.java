package br.dh.meli.integratorprojectfresh.enums;

import org.hibernate.criterion.Order;

public enum OrderStatus {
  PENDING("Aberto"),
  APPROVED("Finalizado");

  OrderStatus(String status) {
  }
}
