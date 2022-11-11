package br.dh.meli.integratorprojectfresh.dto.response;

import br.dh.meli.integratorprojectfresh.model.PurchaseOrderItems;

/**
 * PurchaseOrderItemsDTO
 * Format the order items response to the client
 */
public class PurchaseOrderItemsResponseDTO {
    private Long productId;
    private Integer quantity;

    public PurchaseOrderItemsResponseDTO(PurchaseOrderItems purchaseOrderItems) {
        this.productId = purchaseOrderItems.getAnnouncementId();
        this.quantity = purchaseOrderItems.getProductQuantity();
    }
}
