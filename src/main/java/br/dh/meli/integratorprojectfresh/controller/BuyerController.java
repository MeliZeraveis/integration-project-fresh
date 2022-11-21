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

@RestController
@RequestMapping("/api/v1/fresh-products")
public class BuyerController {

    @Autowired
    BuyerService service;

    @GetMapping("/buyer/order")
    public ResponseEntity<List<PurchaseOrderResponseDTO>> findByBuyerId(@RequestParam Long id) {
        return new ResponseEntity<>(service.getByBuyer(id), HttpStatus.OK);
    }

    @GetMapping("/buyer/orderItems")
    public ResponseEntity<List<OrderItemsResponseDTO>> getOrderWithItems(@RequestParam Long id) {
        return new ResponseEntity<>(service.getOrderWithItems(id), HttpStatus.OK);
    }
}
