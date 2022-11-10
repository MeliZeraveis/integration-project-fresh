package br.dh.meli.integratorprojectfresh.enums;

import org.hibernate.criterion.Order;

public enum OrderStatus {
  PENDING("Pending"),
  APPROVED("Approved");

  OrderStatus(String status) {
  }
}
