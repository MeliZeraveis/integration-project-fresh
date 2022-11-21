package br.dh.meli.integratorprojectfresh.dto.request;

import br.dh.meli.integratorprojectfresh.dto.response.InboundOrderSectionDTO;
import br.dh.meli.integratorprojectfresh.model.BatchStock;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

/**
 * The type Batch stock announcement DTO.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BatchStockAnnouncementDTO {
  private Long batchNumber;
  private int productQuantity;
  private LocalDate dueDate;
  private InboundOrderSectionDTO section;

  /**
   * Instantiates a new Batch stock announcement DTO.
   * @param batchStock the batch stock
   */
  public BatchStockAnnouncementDTO(BatchStock batchStock) {
        this.batchNumber = batchStock.getBatchNumber();
        this.productQuantity = batchStock.getProductQuantity();
        this.dueDate = batchStock.getDueDate();
        this.section = new InboundOrderSectionDTO(batchStock.getInboundOrder().getSectionCode(),
                batchStock.getInboundOrder().getWarehouseCode());
    }
}
