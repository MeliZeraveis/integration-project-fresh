package br.dh.meli.integratorprojectfresh.dto.response;

import br.dh.meli.integratorprojectfresh.model.BatchStock;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The type Batch stock quantity response dto.
 */
@Getter
@Setter
@NoArgsConstructor
public class BatchStockQuantityResponseDTO {

    private Long warehouseCode;
    private Integer totalQuantity;

    /**
     * Instantiates a new Batch stock quantity response dto.
     *
     * @param batchStock the batch stock
     */
    public BatchStockQuantityResponseDTO(BatchStock batchStock) {
        this.warehouseCode = batchStock.getInboundOrder().getWarehouseCode();
        this.totalQuantity = batchStock.getProductQuantity();
    }

    /**
     * Instantiates a new Batch stock quantity response dto.
     *
     * @param id  the id
     * @param num the num
     */
    public BatchStockQuantityResponseDTO(Long id, Integer num) {
       this.warehouseCode = id;
       this.totalQuantity = num;
    }

}