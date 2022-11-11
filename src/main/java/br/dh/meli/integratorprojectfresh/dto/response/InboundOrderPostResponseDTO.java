package br.dh.meli.integratorprojectfresh.dto.response;

import br.dh.meli.integratorprojectfresh.dto.request.BatchStockDTO;
import br.dh.meli.integratorprojectfresh.model.BatchStock;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class InboundOrderPostResponseDTO {
    private List<BatchStockDTO> batchStock;

    public InboundOrderPostResponseDTO(List<BatchStock> batchStock) {
        this.batchStock = batchStock.stream().map(BatchStockDTO::new).collect(Collectors.toList());
    }
}
