package br.dh.meli.integratorprojectfresh.dto.response;

import br.dh.meli.integratorprojectfresh.dto.request.BatchStockDTO;
import br.dh.meli.integratorprojectfresh.model.BatchStock;
import br.dh.meli.integratorprojectfresh.model.OutboundOrder;
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
public class OutboundOrderPutResponseDTO {
  private Long orderNumber;
  private LocalDate orderDate;
  private Long sectionCode;
  private Long warehouseCode;

  private List<BatchStockDTO> batchStock;

  /**
   * Instantiates a new Outbound order put response dto.
   *
   * @param outboundOrderUpdated the outbound order updated
   */
  public OutboundOrderPutResponseDTO(OutboundOrder outboundOrderUpdated) {
    this.orderNumber = outboundOrderUpdated.getOrderNumber();
    this.orderDate = outboundOrderUpdated.getOrderDate();
    this.sectionCode = outboundOrderUpdated.getSectionCode();
    this.warehouseCode = outboundOrderUpdated.getWarehouseCode();

  }

  /**
   * Instantiates a new Outbound order put response dto.
   *
   * @param outboundOrderUpdated  the outbound order updated
   * @param batchStockListUpdated the batch stock list updated
   */
  public OutboundOrderPutResponseDTO(OutboundOrder outboundOrderUpdated, List<BatchStock> batchStockListUpdated) {
    this.orderNumber = outboundOrderUpdated.getOrderNumber();
    this.orderDate = outboundOrderUpdated.getOrderDate();
    this.sectionCode = outboundOrderUpdated.getSectionCode();
    this.warehouseCode = outboundOrderUpdated.getWarehouseCode();
    this.batchStock = batchStockListUpdated.stream().map(BatchStockDTO::new).collect(Collectors.toList());
  }
}
