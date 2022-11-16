package br.dh.meli.integratorprojectfresh.dto.response;

import br.dh.meli.integratorprojectfresh.model.BatchStock;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BatchStockGetResponseDTO {
    List<BatchStockDTOResponseDueDate> batchStock;


    public BatchStockGetResponseDTO(List<BatchStock> batchStock, String category) {
        if(category.equalsIgnoreCase("FS")) {
            this.batchStock = batchStock.stream().filter(b -> Objects.equals(b.getSectionType(), "Fresh")).sorted((p1, p2) -> p2.getBatchNumber().compareTo(p1.getBatchNumber())).map(BatchStockDTOResponseDueDate::new).collect(Collectors.toList());
        }
        else if(category.equalsIgnoreCase("RF")) {
            this.batchStock = batchStock.stream().filter(b -> Objects.equals(b.getSectionType(), "Refrigerated")).sorted(Comparator.comparingInt(BatchStock::getProductQuantity).reversed()).map(BatchStockDTOResponseDueDate::new).collect(Collectors.toList());
        }
        else if(category.equalsIgnoreCase("FF")) {
            this.batchStock = batchStock.stream().filter(b -> Objects.equals(b.getSectionType(), "Frozen")).sorted((p1, p2) -> p1.getDueDate().compareTo(p2.getDueDate())).map(BatchStockDTOResponseDueDate::new).collect(Collectors.toList());
        }
        else {
            this.batchStock = batchStock.stream().map(BatchStockDTOResponseDueDate::new).collect(Collectors.toList());
        }
    }


}
