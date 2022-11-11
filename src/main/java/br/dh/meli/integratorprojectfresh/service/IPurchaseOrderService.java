package br.dh.meli.integratorprojectfresh.service;

import br.dh.meli.integratorprojectfresh.dto.response.PurchaseOrderItemsResponseDTO;
import br.dh.meli.integratorprojectfresh.dto.response.PurchaseOrderResponseDTO;
import br.dh.meli.integratorprojectfresh.dto.request.PurchaseOrderRequestDTO;

import java.util.List;

/**
 * IPurchaseOrderService
 * Interface for the Purchase Order Service
 */
public interface IPurchaseOrderService {
    List<PurchaseOrderItemsResponseDTO> read(Long id);
    PurchaseOrderResponseDTO save(PurchaseOrderRequestDTO purchaseOrder);
    PurchaseOrderResponseDTO update(PurchaseOrderRequestDTO purchaseOrder);
}
