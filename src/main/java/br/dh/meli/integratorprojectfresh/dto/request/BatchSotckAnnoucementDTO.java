package br.dh.meli.integratorprojectfresh.dto.request;

import br.dh.meli.integratorprojectfresh.dto.response.InboundOrderSectionDTO;
import br.dh.meli.integratorprojectfresh.enums.Msg;
import br.dh.meli.integratorprojectfresh.model.BatchStock;
import br.dh.meli.integratorprojectfresh.model.InboundOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Future;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BatchSotckAnnoucementDTO {
    private Long batchNumber;
    private int productQuantity;
    private LocalDate dueDate;

    private InboundOrderSectionDTO section;

    public BatchSotckAnnoucementDTO(BatchStock batchStock) {
        this.batchNumber = batchStock.getBatchNumber();
        this.productQuantity = batchStock.getProductQuantity();
        this.dueDate = batchStock.getDueDate();
        this.section = new InboundOrderSectionDTO(batchStock.getInboundOrder().getSectionCode()
                , batchStock.getInboundOrder().getWarehouseCode());
    }
}
