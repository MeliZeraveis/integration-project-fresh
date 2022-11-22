package br.dh.meli.integratorprojectfresh.dto.response;

import br.dh.meli.integratorprojectfresh.dto.request.BatchStockDTO;
import br.dh.meli.integratorprojectfresh.model.BatchStock;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

/**
 * The type Inbound order post response dto.
 */
@Getter
@Setter
@NoArgsConstructor
public class InboundOrderPostResponseDTO {
    private List<BatchStockDTO> batchStock;

    /**
     * Instantiates a new Inbound order post response dto.
     *
     * @param batchStock the batch stock
     */
    public InboundOrderPostResponseDTO(List<BatchStock> batchStock) {
        this.batchStock = batchStock.stream().map(BatchStockDTO::new).collect(Collectors.toList());
    }
}
