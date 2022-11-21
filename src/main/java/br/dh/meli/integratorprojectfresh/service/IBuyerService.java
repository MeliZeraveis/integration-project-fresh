package br.dh.meli.integratorprojectfresh.service;

import br.dh.meli.integratorprojectfresh.dto.response.OrderItemsResponseDTO;
import br.dh.meli.integratorprojectfresh.dto.response.PurchaseOrderResponseDTO;
import br.dh.meli.integratorprojectfresh.model.Announcement;
import br.dh.meli.integratorprojectfresh.model.PurchaseOrder;
import br.dh.meli.integratorprojectfresh.model.PurchaseOrderItems;

import java.util.List;

public interface IBuyerService {
    List<PurchaseOrderResponseDTO> getByBuyer(Long id);

    List<OrderItemsResponseDTO> getOrderWithItems(Long idBuyer);
}
