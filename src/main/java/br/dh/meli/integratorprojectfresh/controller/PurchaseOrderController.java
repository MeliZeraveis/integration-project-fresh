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

@RestController
@RequestMapping(Routes.BASE_ROUTE)
public class PurchaseOrderController {
  @Autowired
  private PurchaseOrderService purchaseOrderService;

  @GetMapping(Routes.PURCHASE_ORDER)
  public ResponseEntity<List<PurchaseOrderItemsResponseDTO>> read(Long id) {
    return new ResponseEntity<>(purchaseOrderService.read(id), HttpStatus.OK);
  }

  @PostMapping(Routes.PURCHASE_ORDER)
  public ResponseEntity<PurchaseOrderResponseDTO> save(@RequestBody @Valid PurchaseOrderRequestDTO purchaseOrder) {
    return new ResponseEntity<>(purchaseOrderService.save(purchaseOrder), HttpStatus.CREATED);
  }

  @PutMapping(Routes.PURCHASE_ORDER)
  public ResponseEntity<PurchaseOrderResponseDTO> update(@RequestBody @Valid PurchaseOrderRequestDTO purchaseOrder) {
    return new ResponseEntity<>(purchaseOrderService.update(purchaseOrder), HttpStatus.CREATED);
  }
}
