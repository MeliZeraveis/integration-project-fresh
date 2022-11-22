package br.dh.meli.integratorprojectfresh.service;

import br.dh.meli.integratorprojectfresh.dto.response.InboundOrderPutResponseDTO;
import br.dh.meli.integratorprojectfresh.dto.request.InboundOrderRequestDTO;
import br.dh.meli.integratorprojectfresh.dto.response.InboundOrderPostResponseDTO;

/**
 * The interface Inbound order service.
 */
public interface IInboundOrderService {
    /**
     * Save inbound order post response dto.
     *
     * @param inboundOrder the inbound order
     * @return the inbound order post response dto
     */
    InboundOrderPostResponseDTO save(InboundOrderRequestDTO inboundOrder);

    /**
     * Update inbound order put response dto.
     *
     * @param inboundOrder the inbound order
     * @return the inbound order put response dto
     */
    InboundOrderPutResponseDTO update(InboundOrderRequestDTO inboundOrder);
}
