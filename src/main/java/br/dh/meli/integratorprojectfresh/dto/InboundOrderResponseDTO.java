package br.dh.meli.integratorprojectfresh.dto;

import br.dh.meli.integratorprojectfresh.model.BatchStock;
import br.dh.meli.integratorprojectfresh.model.InboundOrder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class InboundOrderResponseDTO {
    private List<BatchStock> batchStock;

    public InboundOrderResponseDTO(List<BatchStock> batchStock) {
        this.batchStock = batchStock;
    }
}
