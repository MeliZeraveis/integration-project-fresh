package br.dh.meli.integratorprojectfresh.dto.request;

import br.dh.meli.integratorprojectfresh.enums.Msg;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Positive;
import java.time.LocalDate;
import java.util.List;

/**
 * The type Outbound order dto.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OutboundOrderDTO {
  private Long orderNumber;

  @NotNull(message = Msg.DATE_REQUIRED)
  @PastOrPresent(message = Msg.DATE_PAST_OR_PRESENT)
  private LocalDate orderDate;

  @NotNull( message = Msg.SECTION_CODE_REQUIRED)
  @Positive(message = Msg.SECTION_CODE_POSITIVE)
  private Long sectionCode;

  @NotNull( message = Msg.WAREHOUSE_CODE_REQUIRED)
  private Long warehouseCode;

  @NotEmpty(message = Msg.BATCHSTOCK_NOT_EMPTY)
  private List<@NotNull(message = Msg.BATCH_ID_NOT_NULL) @Positive(message = Msg.BATCH_ID_NOT_VALID) Long> batchIds;
}