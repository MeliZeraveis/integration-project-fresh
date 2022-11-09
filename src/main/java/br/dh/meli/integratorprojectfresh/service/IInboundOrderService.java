package br.dh.meli.integratorprojectfresh.service;

import br.dh.meli.integratorprojectfresh.dto.InboundOrderPutResponseDTO;
import br.dh.meli.integratorprojectfresh.dto.InboundOrderRequestDTO;
import br.dh.meli.integratorprojectfresh.dto.InboundOrderPostResponseDTO;

public interface IInboundOrderService {
    InboundOrderPostResponseDTO save(InboundOrderRequestDTO inboundOrder);
    InboundOrderPutResponseDTO update(InboundOrderRequestDTO inboundOrder);
}
