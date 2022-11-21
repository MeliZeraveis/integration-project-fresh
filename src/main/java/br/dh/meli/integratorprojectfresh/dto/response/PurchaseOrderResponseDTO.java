package br.dh.meli.integratorprojectfresh.dto.response;

import br.dh.meli.integratorprojectfresh.model.PurchaseOrder;
import br.dh.meli.integratorprojectfresh.model.PurchaseOrderItems;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * PurchaseOrderResponseDTO
 * Format the Purchase Order response data to the client
 */
@Getter
@Setter
@NoArgsConstructor
public class PurchaseOrderResponseDTO {
  private Long id;
  private String date;
  private String orderStatus;
  private BigDecimal totalPrice;
  private List<PurchaseOrderItemsResponseDTO> products;

  public PurchaseOrderResponseDTO(PurchaseOrder purchaseOrder, List<PurchaseOrderItems> products) {
    this.id = purchaseOrder.getId();
    this.date = purchaseOrder.getDate().toString();
    this.orderStatus = purchaseOrder.getStatus();
    this.totalPrice = purchaseOrder.getTotal();
    this.products = products.stream().map(PurchaseOrderItemsResponseDTO::new).collect(Collectors.toList());
  }

  public PurchaseOrderResponseDTO(PurchaseOrder purchaseOrder) {
    this.id = purchaseOrder.getId();
    this.date = purchaseOrder.getDate().toString();
    this.orderStatus = purchaseOrder.getStatus();
    this.totalPrice = purchaseOrder.getTotal();
  }
}
