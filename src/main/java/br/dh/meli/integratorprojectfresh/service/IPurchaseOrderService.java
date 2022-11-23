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
    /**
     * Read list.
     *
     * @param id the id
     * @return the list
     */
    List<PurchaseOrderItemsResponseDTO> read(Long id);

    /**
     * Save purchase order response dto.
     *
     * @param purchaseOrder the purchase order
     * @return the purchase order response dto
     */
    PurchaseOrderResponseDTO save(PurchaseOrderRequestDTO purchaseOrder);

    /**
     * Update purchase order response dto.
     *
     * @param id the id
     * @return the purchase order response dto
     */
    PurchaseOrderResponseDTO update(Long id);
}
