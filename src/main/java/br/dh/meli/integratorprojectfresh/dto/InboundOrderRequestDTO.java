package br.dh.meli.integratorprojectfresh.dto;

import br.dh.meli.integratorprojectfresh.model.InboundOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InboundOrderRequestDTO {
    private InboundOrder inboundOrder;
}
