package br.dh.meli.integratorprojectfresh.dto.request;

import br.dh.meli.integratorprojectfresh.annotations.OneOf;
import br.dh.meli.integratorprojectfresh.enums.Msg;
import br.dh.meli.integratorprojectfresh.model.PurchaseOrder;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * PurchaseOrderRequestDTO
 * Validate incoming Purchase Order data from the client
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseOrderRequestDTO {
  @NotNull(message = Msg.PURCHASE_ORDER_DATE_NOT_NULL)
  @PastOrPresent(message = Msg.PURCHASE_ORDER_DATE_NOT_VALID)
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime date;

  @NotNull(message = Msg.BUYER_ID_NOT_NULL)
  @Positive(message = Msg.BUYER_ID_NOT_VALID)
  private Long buyerId;

  @NotNull(message = Msg.PURCHASE_ORDER_STATUS_NOT_NULL)
  @OneOf(value = {"Aberto", "Finalizado"}, message = Msg.PURCHASE_ORDER_STATUS_NOT_VALID)
  private String orderStatus;

  @NotEmpty(message = Msg.PURCHASE_ORDER_ITEMS_NOT_EMPTY)
  private List<@Valid PurchaseOrderItemsRequestDTO> products;

  private BigDecimal total;
}
