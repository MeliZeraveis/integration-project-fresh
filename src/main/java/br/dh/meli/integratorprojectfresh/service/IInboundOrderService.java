package br.dh.meli.integratorprojectfresh.service;

import br.dh.meli.integratorprojectfresh.dto.InboundOrderRequestDTO;
import br.dh.meli.integratorprojectfresh.dto.InboundOrderResponseDTO;
import br.dh.meli.integratorprojectfresh.model.InboundOrder;

public interface IInboundOrderService {
    InboundOrderResponseDTO save(InboundOrderRequestDTO inboundOrder);
}
