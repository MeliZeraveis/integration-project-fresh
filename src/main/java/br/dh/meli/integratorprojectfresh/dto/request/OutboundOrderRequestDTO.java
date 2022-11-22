package br.dh.meli.integratorprojectfresh.dto.request;

import br.dh.meli.integratorprojectfresh.enums.Msg;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * The type Outbound order request dto.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OutboundOrderRequestDTO {
  /**
   * The Outbound order.
   */
  @NotNull(message = Msg.DATE_REQUIRED)
  @Valid OutboundOrderDTO outboundOrder;
}
