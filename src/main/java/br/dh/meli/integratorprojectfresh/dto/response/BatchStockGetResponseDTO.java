package br.dh.meli.integratorprojectfresh.dto.response;

import br.dh.meli.integratorprojectfresh.dto.request.BatchSotckAnnoucementDTO;
import br.dh.meli.integratorprojectfresh.model.BatchStock;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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

    public BatchStockGetResponseDTO(List<BatchStock> batchStock, String category) {
        this.batchNumber = batchStock.getBatchNumber();
        this.annoucementId = batchStock.getAnnouncementId();
        this.section = batchStock.getSectionType();
        this.dueDate = batchStock.getDueDate();
        this.productQuantity = batchStock.getProductQuantity();
        if(category.equalsIgnoreCase("FS")) {
            this.batchStock = batchStock.stream().sorted((p1, p2) -> p2.getBatchNumber().compareTo(p1.getBatchNumber())).map(BatchSotckAnnoucementDTO::new).collect(Collectors.toList());
        }
        else if(category.equalsIgnoreCase("RF")) {
            this.batchStock = batchStock.stream().sorted(Comparator.comparingInt(BatchStock::getProductQuantity).reversed()).map(BatchSotckAnnoucementDTO::new).collect(Collectors.toList());
        }
        else if(category.equalsIgnoreCase("FF")) {
            this.batchStock = batchStock.stream().sorted((p1, p2) -> p1.getDueDate().compareTo(p2.getDueDate())).map(BatchSotckAnnoucementDTO::new).collect(Collectors.toList());
        }
        else {
            this.batchStock = batchStock.stream().map(BatchSotckAnnoucementDTO::new).collect(Collectors.toList());
        }
    }
}
