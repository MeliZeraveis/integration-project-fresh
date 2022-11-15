package br.dh.meli.integratorprojectfresh.dto.response;

import br.dh.meli.integratorprojectfresh.model.BatchStock;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BatchStockQuantityResponseDTO {

    private Long warehouseCode;
    private int productQuantity;

    public BatchStockQuantityResponseDTO(BatchStock batchStock) {
        this.warehouseCode = batchStock.getInboundOrder().getWarehouseCode();
        this.productQuantity = batchStock.getProductQuantity();
    }
}