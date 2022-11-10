package br.dh.meli.integratorprojectfresh.dto.request;

import br.dh.meli.integratorprojectfresh.enums.Msg;
import br.dh.meli.integratorprojectfresh.model.BatchStock;
import br.dh.meli.integratorprojectfresh.model.InboundOrder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Positive;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class InboundOrderRequestDTO {
    InboundOrder inboundOrder;

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
    private List<@Valid BatchStockDTO> batchStock;

    public InboundOrderRequestDTO(InboundOrder inboundOrder) {
        this.orderDate = inboundOrder.getOrderDate();
        this.sectionCode = inboundOrder.getSectionCode();
        this.warehouseCode = inboundOrder.getWarehouseCode();
        this.batchStock = inboundOrder.getBatchStock().stream().map(BatchStockDTO::new).collect(Collectors.toList());
        if(orderNumber != null) {
            this.orderNumber = inboundOrder.getOrderNumber();
        }

        System.out.println("InboundOrderRequestDTO: " + inboundOrder);
    }
}
