package br.dh.meli.integratorprojectfresh.service;

import br.dh.meli.integratorprojectfresh.dto.response.OrderItemsResponseDTO;
import br.dh.meli.integratorprojectfresh.dto.response.PurchaseOrderResponseDTO;
import br.dh.meli.integratorprojectfresh.model.Announcement;
import br.dh.meli.integratorprojectfresh.model.PurchaseOrder;
import br.dh.meli.integratorprojectfresh.model.PurchaseOrderItems;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface IBuyerService {
       List<OrderItemsResponseDTO> getOrderWithItems(Long idBuyer);

       List<OrderItemsResponseDTO> findPurchaseOrderByStatus( Long id, String status);
}
