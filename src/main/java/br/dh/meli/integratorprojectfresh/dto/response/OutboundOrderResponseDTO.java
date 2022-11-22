package br.dh.meli.integratorprojectfresh.dto.response;

import br.dh.meli.integratorprojectfresh.dto.request.BatchStockDTO;
import br.dh.meli.integratorprojectfresh.model.OutboundOrder;
import br.dh.meli.integratorprojectfresh.model.OutboundOrderBatches;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The type Outbound order put response dto.
 */
@Getter
@Setter
@NoArgsConstructor
public class OutboundOrderResponseDTO {
  private Long orderNumber;
  private LocalDate orderDate;
  private Long sectionCode;
  private Long warehouseCode;
  private List<OutboundOrderBatches> batches;


  /**
   * Instantiates a new Outbound order put response dto.
   *
   * @param outboundOrder the outbound order
   * @param batchStockList the batch stock list
   */
  public OutboundOrderResponseDTO(OutboundOrder outboundOrder, List<OutboundOrderBatches> batchStockList) {
    this.orderNumber = outboundOrder.getOrderNumber();
    this.orderDate = outboundOrder.getOrderDate();
    this.sectionCode = outboundOrder.getSectionCode();
    this.warehouseCode = outboundOrder.getWarehouseCode();
    this.batches = batchStockList;
  }
}