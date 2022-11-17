package br.dh.meli.integratorprojectfresh.controller;

import br.dh.meli.integratorprojectfresh.dto.response.PurchaseOrderItemsResponseDTO;
import br.dh.meli.integratorprojectfresh.dto.request.PurchaseOrderRequestDTO;
import br.dh.meli.integratorprojectfresh.dto.response.PurchaseOrderResponseDTO;
import br.dh.meli.integratorprojectfresh.enums.Routes;
import br.dh.meli.integratorprojectfresh.service.PurchaseOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * PurchaseOrderController
 * Controller to manage the Purchase Order requests
 */
@RestController
@RequestMapping(Routes.BASE_ROUTE)
public class PurchaseOrderController {
  @Autowired
  private PurchaseOrderService purchaseOrderService;

  /**
   * read
   * Get all Purchase Order items from a specified Purchase Order
   * The request must be made to the endpoint /api/v1/fresh-product/orders/{id}
   * The request must be a GET with the id of the Purchase Order
   * @param id - the id of the Purchase Order
   * @return ResponseEntity<List<PurchaseOrderItemsResponseDTO>> - the Purchase Order items
   */
  @GetMapping(Routes.PURCHASE_ORDER)
  public ResponseEntity<List<PurchaseOrderItemsResponseDTO>> read(Long id) {
    return new ResponseEntity<>(purchaseOrderService.read(id), HttpStatus.OK);
  }

  /**
   * save
   * Register a new Purchase Order in the database, and return the Purchase Order data
   * The request must be a POST with a PurchaseOrderRequestDTO object in the request body
   * @param purchaseOrder - the Purchase Order data to be registered
   * @return ResponseEntity<PurchaseOrderResponseDTO>
   */
  @PostMapping(Routes.PURCHASE_ORDER)
  public ResponseEntity<PurchaseOrderResponseDTO> save(@RequestBody @Valid PurchaseOrderRequestDTO purchaseOrder) {
    return new ResponseEntity<>(purchaseOrderService.save(purchaseOrder), HttpStatus.CREATED);
  }

  /**
   * update
   * Replace an existing Purchase Order in the database, and return the Purchase Order data
   * The request must be a PUT with a PurchaseOrderRequestDTO object in the request body
   * @param id - the Purchase Order id data to be replaced
   * @return ResponseEntity<PurchaseOrderResponseDTO>
   */
  @PutMapping(Routes.PURCHASE_ORDER)
  public ResponseEntity<PurchaseOrderResponseDTO> update(Long id) {
    return new ResponseEntity<>(purchaseOrderService.update(id), HttpStatus.CREATED);
  }
}
