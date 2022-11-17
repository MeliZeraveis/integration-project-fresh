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
public class BatchStockGetResponseDTO {
    List<BatchStockDTOResponseDueDate> batchStock;
    public BatchStockGetResponseDTO(List<BatchStock> batchStock) {
        this.batchStock = batchStock.stream().map(BatchStockDTOResponseDueDate::new).collect(Collectors.toList());
    }


}
