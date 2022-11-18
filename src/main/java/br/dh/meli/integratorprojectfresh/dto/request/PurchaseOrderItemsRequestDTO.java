package br.dh.meli.integratorprojectfresh.dto.request;

import br.dh.meli.integratorprojectfresh.enums.Msg;
import br.dh.meli.integratorprojectfresh.model.PurchaseOrderItems;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

/**
 * PurchaseOrderItemsDTO
 * Validate incoming Purchase Order Items data from the client
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseOrderItemsRequestDTO {
  private Long purchaseOrderId;

  @NotNull(message = Msg.ANNOUNCEMENT_ID_NOT_NULL)
  @Positive(message = Msg.ANNOUNCEMENT_ID_NOT_VALID)
  private Long announcementId;

  @NotNull(message = Msg.PRODUCT_QUANTITY_NOT_NULL)
  @Positive(message = Msg.PRODUCT_QUANTITY_NOT_VALID)
  private Integer productQuantity;

  private BigDecimal productPrice;

  /**
   * toPurchaseOrderItems
   * Convert DTO to Model
   * @return PurchaseOrderItems
   */
  public PurchaseOrderItems toPurchaseOrderItems() {
    return new PurchaseOrderItems(purchaseOrderId, announcementId, productQuantity, productPrice);
  }
}
