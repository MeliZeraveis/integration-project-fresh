package br.dh.meli.integratorprojectfresh.service;

import br.dh.meli.integratorprojectfresh.dto.request.UserDTO;
import br.dh.meli.integratorprojectfresh.dto.response.OrderItemsResponseDTO;
import br.dh.meli.integratorprojectfresh.dto.response.PurchaseOrderResponseDTO;
import br.dh.meli.integratorprojectfresh.enums.Msg;
import br.dh.meli.integratorprojectfresh.exception.NotFoundException;
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


    List<PurchaseOrder> NotFoundId(Long id) {
        List<PurchaseOrder> purchaseOrderList = purchaseOrderRepo.findPurchaseOrderByBuyerId(id);
        if(purchaseOrderList.size()==0) {
            throw new NotFoundException(Msg.USER_ID_NOT_FOUND);
        }
        return purchaseOrderList;
    }
    @Override
    public List<OrderItemsResponseDTO> getOrderWithItems(Long idBuyer) {
        List<PurchaseOrder> purchaseOrderList = NotFoundId(idBuyer);
        List<OrderItemsResponseDTO> orderItemsResponseDTOS= purchaseOrderList.stream().map(p -> new OrderItemsResponseDTO(p, purchaseOrderItemsRepo.findByPurchaseOrderId(p.getId()))).collect(Collectors.toList());
        return orderItemsResponseDTOS;
    }

    @Override
    public List<OrderItemsResponseDTO> findPurchaseOrderByStatus(Long id, String status) {
        List<PurchaseOrder> purchaseOrderList = NotFoundId(id);
        List<OrderItemsResponseDTO> orderItemsResponseDTOS= purchaseOrderList.stream().map(p -> new OrderItemsResponseDTO(p, purchaseOrderItemsRepo.findByPurchaseOrderId(p.getId()))).collect(Collectors.toList());
        List<OrderItemsResponseDTO> listDTOS = new ArrayList<>();
        for(int i = 0; i<orderItemsResponseDTOS.size(); i++) {
            if(orderItemsResponseDTOS.get(i).getOrderStatus().equalsIgnoreCase(status)) {
                listDTOS.add(orderItemsResponseDTOS.get(i));
            }
        }
        if(listDTOS.size()==0){
            throw new NotFoundException(Msg.STATUS_NOT_VALID);
        }
        return listDTOS;
    }


}
