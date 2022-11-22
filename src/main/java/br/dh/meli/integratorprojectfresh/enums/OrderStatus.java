package br.dh.meli.integratorprojectfresh.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.criterion.Order;

/**
 * The enum OrderStatus lists the possible statuses of a purchase order.
 */
@Getter
@AllArgsConstructor
public enum OrderStatus {
    /**
     * Pending order status.
     */
    PENDING("Aberto"),
    /**
     * Approved order status.
     */
    APPROVED("Finalizado");

    /**
     * The Name.
     */
    public final String name;
}
