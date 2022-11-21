package br.dh.meli.integratorprojectfresh.service;

import br.dh.meli.integratorprojectfresh.dto.response.InboundOrderPutResponseDTO;
import br.dh.meli.integratorprojectfresh.dto.request.InboundOrderRequestDTO;
import br.dh.meli.integratorprojectfresh.dto.response.InboundOrderPostResponseDTO;

public interface IInboundOrderService {
    InboundOrderPostResponseDTO save(InboundOrderRequestDTO inboundOrder);
    InboundOrderPutResponseDTO update(InboundOrderRequestDTO inboundOrder);
}
