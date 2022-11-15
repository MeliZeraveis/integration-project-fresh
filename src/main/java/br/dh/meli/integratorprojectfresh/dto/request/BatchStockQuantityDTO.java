package br.dh.meli.integratorprojectfresh.dto.request;

import br.dh.meli.integratorprojectfresh.model.BatchStock;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BatchStockQuantityDTO {

    private int productQuantity;
    private Long warehouseCode;

    public BatchStockQuantityDTO(BatchStock batchStock) {
        this.productQuantity = batchStock.getProductQuantity();
        this.warehouseCode = batchStock.getInboundOrder().getWarehouseCode();
    }
}
