package br.dh.meli.integratorprojectfresh.dto.request;

import br.dh.meli.integratorprojectfresh.enums.Msg;
import br.dh.meli.integratorprojectfresh.model.BatchStock;
import br.dh.meli.integratorprojectfresh.model.InboundOrder;
import br.dh.meli.integratorprojectfresh.model.Section;
import br.dh.meli.integratorprojectfresh.model.Warehouse;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
//@AllArgsConstructor
public class InboundOrderRequestDTO {
    @NotNull( message = Msg.FIELD_REQUIRED)
    private Long orderNumber;
    private LocalDate orderDate;
    @NotNull( message = Msg.FIELD_REQUIRED)
    private Long sectionCode;

    @NotNull( message = Msg.FIELD_REQUIRED)
    private Long warehouseCode;

    private Warehouse warehouse;

    @NotEmpty(message = Msg.FIELD_NOT_EMPTY)
    private List<BatchStock> batchStock;

    private  Section section;

    public InboundOrderRequestDTO(InboundOrder inboundOrder) {
        this.orderDate = inboundOrder.getOrderDate();
        this.sectionCode = inboundOrder.getSectionCode();
        this.warehouseCode = inboundOrder.getWarehouseCode();
        this.warehouse = inboundOrder.getWarehouse();
        this.batchStock = inboundOrder.getBatchStock();
        this.section = inboundOrder.getSection();
        if(orderNumber != null) {
            this.orderNumber = inboundOrder.getOrderNumber();
        }
    }
}
