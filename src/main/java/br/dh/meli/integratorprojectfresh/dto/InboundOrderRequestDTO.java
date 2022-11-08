package br.dh.meli.integratorprojectfresh.dto;

import br.dh.meli.integratorprojectfresh.model.InboundOrder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class InboundOrderRequestDTO {
    private InboundOrder inboundOrder;
}
