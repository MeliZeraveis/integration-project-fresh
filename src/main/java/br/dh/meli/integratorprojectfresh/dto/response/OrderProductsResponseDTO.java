package br.dh.meli.integratorprojectfresh.dto.response;

import br.dh.meli.integratorprojectfresh.model.PurchaseOrderItems;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The type Order products response dto.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderProductsResponseDTO {
    private String productName;
    private Integer quantity;

    /**
     * Instantiates a new Order products response dto.
     *
     * @param purchaseOrderItems the purchase order items
     */
    public OrderProductsResponseDTO(PurchaseOrderItems purchaseOrderItems) {
        this.productName = purchaseOrderItems.getAnnouncement().getName();
        this.quantity = purchaseOrderItems.getProductQuantity();
    }

}
