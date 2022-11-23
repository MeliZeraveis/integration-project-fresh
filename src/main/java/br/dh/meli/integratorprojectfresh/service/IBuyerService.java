package br.dh.meli.integratorprojectfresh.service;

import br.dh.meli.integratorprojectfresh.dto.response.OrderItemsResponseDTO;
import br.dh.meli.integratorprojectfresh.dto.response.PurchaseOrderResponseDTO;
import br.dh.meli.integratorprojectfresh.model.Announcement;
import br.dh.meli.integratorprojectfresh.model.PurchaseOrder;
import br.dh.meli.integratorprojectfresh.model.PurchaseOrderItems;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * The interface Buyer service.
 */
public interface IBuyerService {
    /**
     * Gets order with items.
     *
     * @param idBuyer the id buyer
     * @return the order with items
     */
    List<OrderItemsResponseDTO> getOrderWithItems(Long idBuyer);

    /**
     * Find purchase order by status list.
     *
     * @param id     the id
     * @param status the status
     * @return the list
     */
    List<OrderItemsResponseDTO> findPurchaseOrderByStatus( Long id, String status);
}
