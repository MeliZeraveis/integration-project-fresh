package br.dh.meli.integratorprojectfresh.controller;


import br.dh.meli.integratorprojectfresh.dto.request.UserDTO;
import br.dh.meli.integratorprojectfresh.dto.response.OrderItemsResponseDTO;
import br.dh.meli.integratorprojectfresh.dto.response.PurchaseOrderResponseDTO;
import br.dh.meli.integratorprojectfresh.model.PurchaseOrderItems;
import br.dh.meli.integratorprojectfresh.service.BuyerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * The type Buyer controller.
 */
@RestController
@RequestMapping("/api/v1/fresh-products")
public class BuyerController {

    /**
     * The Service.
     */
    @Autowired
    BuyerService service;

    /**
     * Gets order with items.
     *
     * @param id the id
     * @return the order with items
     */
    @GetMapping("/buyer/orderItems")
    public ResponseEntity<List<OrderItemsResponseDTO>> getOrderWithItems(@RequestParam Long id) {
        return new ResponseEntity<>(service.getOrderWithItems(id), HttpStatus.OK);
    }

    /**
     * Gets order by status.
     *
     * @param id     the id
     * @param status the status
     * @return the order by status
     */
    @GetMapping("/buyer/status")
    public ResponseEntity<List<OrderItemsResponseDTO>> getOrderByStatus(@RequestParam Long id, @RequestParam String status) {
        return new ResponseEntity<>(service.findPurchaseOrderByStatus(id, status), HttpStatus.OK);
    }

}
