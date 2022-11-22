package br.dh.meli.integratorprojectfresh.dto.response;

import br.dh.meli.integratorprojectfresh.model.BatchStock;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

/**
 * The type Batch stock get response dto.
 */
@Getter
@Setter
@NoArgsConstructor
public class BatchStockGetResponseDTO {
    /**
     * The Batch stock.
     */
    List<BatchStockDTOResponseDueDate> batchStock;

    /**
     * Instantiates a new Batch stock get response dto.
     *
     * @param batchStock the batch stock
     */
    public BatchStockGetResponseDTO(List<BatchStock> batchStock) {
        this.batchStock = batchStock.stream().map(BatchStockDTOResponseDueDate::new).collect(Collectors.toList());
    }
}
