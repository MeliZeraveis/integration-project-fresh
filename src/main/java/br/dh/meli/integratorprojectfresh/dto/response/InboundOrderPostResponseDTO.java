package br.dh.meli.integratorprojectfresh.dto.response;

import br.dh.meli.integratorprojectfresh.dto.request.BatchStockDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class InboundOrderPostResponseDTO {
    private List<BatchStockDTO> batchStock;

    public InboundOrderPostResponseDTO(List<BatchStockDTO> batchStock) {
        this.batchStock = batchStock;
    }
}
