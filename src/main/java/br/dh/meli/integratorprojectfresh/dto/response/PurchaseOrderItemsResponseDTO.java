package br.dh.meli.integratorprojectfresh.dto.response;

import br.dh.meli.integratorprojectfresh.model.PurchaseOrderItems;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * PurchaseOrderItemsDTO
 * Format the order items response to the client
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseOrderItemsResponseDTO {
    private Long productId;
    private Integer quantity;

    /**
     * Instantiates a new Purchase order items response dto.
     *
     * @param purchaseOrderItems the purchase order items
     */
    public PurchaseOrderItemsResponseDTO(PurchaseOrderItems purchaseOrderItems) {
        this.productId = purchaseOrderItems.getAnnouncementId();
        this.quantity = purchaseOrderItems.getProductQuantity();
    }
}
