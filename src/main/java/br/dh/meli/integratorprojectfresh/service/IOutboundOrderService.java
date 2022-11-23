package br.dh.meli.integratorprojectfresh.service;

import br.dh.meli.integratorprojectfresh.dto.request.OutboundOrderRequestDTO;
import br.dh.meli.integratorprojectfresh.dto.response.OutboundOrderResponseDTO;

/**
 * The interface for Outbound orders service.
 */
public interface IOutboundOrderService {
  /**
   * Read all items from an outbound order.
   *
   * @param id the id of the outbound order
   * @return List of BatchStockGetResponseDTO
   */
  OutboundOrderResponseDTO read(Long id);

  /**
   * Save outbound order post response DTO.
   *
   * @param outboundOrder the outbound order
   * @return the outbound order post response DTO
   */
  OutboundOrderResponseDTO save(OutboundOrderRequestDTO outboundOrder);

  /**
   * Update outbound order put response DTO.
   *
   * @param outboundOrder the outbound order
   * @return the outbound order put response DTO
   */
  OutboundOrderResponseDTO update(OutboundOrderRequestDTO outboundOrder);
}

