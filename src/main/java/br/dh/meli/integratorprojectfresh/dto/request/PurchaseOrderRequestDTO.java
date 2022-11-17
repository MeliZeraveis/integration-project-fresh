package br.dh.meli.integratorprojectfresh.dto.request;

import br.dh.meli.integratorprojectfresh.annotations.EnumNamePattern;
import br.dh.meli.integratorprojectfresh.enums.Msg;
import br.dh.meli.integratorprojectfresh.enums.OrderStatus;
import br.dh.meli.integratorprojectfresh.model.PurchaseOrder;
import br.dh.meli.integratorprojectfresh.model.PurchaseOrderItems;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * PurchaseOrderRequestDTO
 * Validate incoming Purchase Order data from the client
 */
@Getter
@Setter
@NoArgsConstructor
public class PurchaseOrderRequestDTO {
  @NotNull(message = Msg.PURCHASE_ORDER_DATE_NOT_NULL)
  @PastOrPresent(message = Msg.PURCHASE_ORDER_DATE_NOT_VALID)
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime date;

  @NotNull(message = Msg.BUYER_ID_NOT_NULL)
  @Positive(message = Msg.BUYER_ID_NOT_VALID)
  private Long buyerId;

  @NotNull(message = Msg.PURCHASE_ORDER_STATUS_NOT_NULL)
  // @EnumNamePattern(regexp = "Aberto|Finalizado", message = Msg.PURCHASE_ORDER_STATUS_NOT_VALID)
  private OrderStatus orderStatus;

  @NotEmpty(message = Msg.PURCHASE_ORDER_ITEMS_NOT_EMPTY)
  private List<@Valid PurchaseOrderItemsRequestDTO> products;

  private BigDecimal total;

  public PurchaseOrderRequestDTO(LocalDateTime date, Long buyerId, OrderStatus orderStatus, List<PurchaseOrderItems> products) {
    this.date = date;
    this.buyerId = buyerId;
    this.orderStatus = orderStatus;
    this.products = products.stream().map(PurchaseOrderItemsRequestDTO::new).collect(Collectors.toList());
    products.forEach(p -> System.out.println(p.getProductQuantity()));
  }

  /**
   * toPurchaseOrder
   * Convert DTO to Model
   * @return PurchaseOrder
   */
  public PurchaseOrder toPurchaseOrder() {
    return new PurchaseOrder(this.date, this.orderStatus, BigDecimal.ZERO, this.buyerId);
  }
}
