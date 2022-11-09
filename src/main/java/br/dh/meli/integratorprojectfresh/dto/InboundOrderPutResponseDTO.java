package br.dh.meli.integratorprojectfresh.dto;

import br.dh.meli.integratorprojectfresh.model.InboundOrder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class InboundOrderPutResponseDTO {
    private Long orderNumber;
    private LocalDate orderDate;
    private Long sectionCode;
    private Long warehouseCode;

    public InboundOrderPutResponseDTO(InboundOrder inboundOrderUpdated) {
        this.orderNumber = inboundOrderUpdated.getOrderNumber();
        this.orderDate = inboundOrderUpdated.getOrderDate();
        this.sectionCode = inboundOrderUpdated.getSectionCode();
        this.warehouseCode = inboundOrderUpdated.getWarehouseCode();
    }
}
