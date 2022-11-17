package br.dh.meli.integratorprojectfresh.enums;

import org.hibernate.criterion.Order;

/**
 * The enum Order status.
 */
public enum OrderStatus {
  PENDING("Pending"),
  APPROVED("Approved");

  OrderStatus(String status) {
  }
}
