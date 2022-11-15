package br.dh.meli.integratorprojectfresh.dto.response;

import br.dh.meli.integratorprojectfresh.model.BatchStock;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BatchStockGetResponseDTO {
    private Long batchNumber;
    private Long annoucementId;
    private String section;
    private LocalDate dueDate;
    private int productQuantity;

    public BatchStockGetResponseDTO(BatchStock batchStock) {
        this.batchNumber = batchStock.getBatchNumber();
        this.annoucementId = batchStock.getAnnouncementId();
        this.section = batchStock.getSectionType();
        this.dueDate = batchStock.getDueDate();
        this.productQuantity = batchStock.getProductQuantity();
    }
}
