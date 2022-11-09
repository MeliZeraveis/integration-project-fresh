package br.dh.meli.integratorprojectfresh.dto;

import br.dh.meli.integratorprojectfresh.model.InboundOrder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class InboundOrderPutResponseDTO {
    private Long orderNumber;
    private LocalDate orderDate;
    private Long sectionCode;
    private Long warehouseCode;

    private List<BatchStockDTO> batchStock;

    public InboundOrderPutResponseDTO(InboundOrder inboundOrderUpdated) {
        this.orderNumber = inboundOrderUpdated.getOrderNumber();
        this.orderDate = inboundOrderUpdated.getOrderDate();
        this.sectionCode = inboundOrderUpdated.getSectionCode();
        this.warehouseCode = inboundOrderUpdated.getWarehouseCode();

    }

    public InboundOrderPutResponseDTO(InboundOrder inboundOrderUpdated, List<BatchStockDTO> batchStockListUpdated) {
        this.orderNumber = inboundOrderUpdated.getOrderNumber();
        this.orderDate = inboundOrderUpdated.getOrderDate();
        this.sectionCode = inboundOrderUpdated.getSectionCode();
        this.warehouseCode = inboundOrderUpdated.getWarehouseCode();
        this.batchStock = batchStockListUpdated;
    }
}
