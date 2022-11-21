package br.dh.meli.integratorprojectfresh.service;

import br.dh.meli.integratorprojectfresh.dto.request.UserDTO;
import br.dh.meli.integratorprojectfresh.dto.response.OrderItemsResponseDTO;
import br.dh.meli.integratorprojectfresh.dto.response.PurchaseOrderResponseDTO;
import br.dh.meli.integratorprojectfresh.model.PurchaseOrder;
import br.dh.meli.integratorprojectfresh.model.PurchaseOrderItems;
import br.dh.meli.integratorprojectfresh.repository.PurchaseOrderItemsRepository;
import br.dh.meli.integratorprojectfresh.repository.PurchaseOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BuyerService implements IBuyerService{
    @Autowired
    PurchaseOrderRepository purchaseOrderRepo;

    @Autowired
    PurchaseOrderItemsRepository purchaseOrderItemsRepo;

    @Override
    public List<PurchaseOrderResponseDTO> getByBuyer(Long id) {
        List<PurchaseOrder> purchaseOrderList = purchaseOrderRepo.findPurchaseOrderByBuyerId(id);
        List<PurchaseOrderResponseDTO> purchaseOrderResponseDTOS = purchaseOrderList.stream().map(PurchaseOrderResponseDTO::new).collect(Collectors.toList());
        return purchaseOrderResponseDTOS;
    }

    @Override
    public List<OrderItemsResponseDTO> getOrderWithItems(Long idBuyer) {
        List<PurchaseOrder> purchaseOrderList = purchaseOrderRepo.findPurchaseOrderByBuyerId(idBuyer);
        List<OrderItemsResponseDTO> orderItemsResponseDTOS= purchaseOrderList.stream().map(p -> new OrderItemsResponseDTO(p, purchaseOrderItemsRepo.findByPurchaseOrderId(p.getId()))).collect(Collectors.toList());
        return orderItemsResponseDTOS;
    }
}
