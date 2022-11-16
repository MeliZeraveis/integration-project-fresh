package br.dh.meli.integratorprojectfresh.dto.response;

import br.dh.meli.integratorprojectfresh.model.BatchStock;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class BatchStockQuantityResponseDTO {

    private Long warehouseCode;
    private Integer totalQuantity;

    public BatchStockQuantityResponseDTO(BatchStock batchStock) {
        this.warehouseCode = batchStock.getInboundOrder().getWarehouseCode();
        this.totalQuantity = batchStock.getProductQuantity();
    }

    public BatchStockQuantityResponseDTO(Long id, Integer num) {
       this.warehouseCode = id;
       this.totalQuantity = num;
    }

}