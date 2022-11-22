package br.dh.meli.integratorprojectfresh.controller;

import br.dh.meli.integratorprojectfresh.dto.request.OutboundOrderRequestDTO;
import br.dh.meli.integratorprojectfresh.dto.response.BatchStockGetResponseDTO;
import br.dh.meli.integratorprojectfresh.dto.response.OutboundOrderResponseDTO;
import br.dh.meli.integratorprojectfresh.enums.Routes;
import br.dh.meli.integratorprojectfresh.service.IOutboundOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * OutboundOrderController
 * Controller to manage the Outbound Order requests
 */
@RestController
@RequestMapping(Routes.BASE_ROUTE)
public class OutboundOrderController {
  @Autowired
  private IOutboundOrderService service;

  /**
   * read
   * Get all Outbound Order items from a specified Outbound Order
   * The request must be made to the endpoint /api/v1/fresh-product/outbound-orders/{id}
   * The request must be a GET with the id of the Outbound Order
   *
   * @param id - the id of the Outbound Order
   * @return ResponseEntity<BatchStockGetResponseDTO> - the Outbound Order items
   */
  @GetMapping(Routes.OUTBOUND_ORDER)
  public ResponseEntity<BatchStockGetResponseDTO> read(Long id) {
    return ResponseEntity.ok(service.read(id));
  }

  /**
   * save
   * Register a new Outbound Order in the database, and return the Outbound Order data
   * The request must be a POST with a OutboundOrderRequestDTO object in the request body
   *
   * @param outboundOrder the Outbound Order data to be registered
   * @return ResponseEntity<OutboundOrderResponseDTO>  response entity
   */
  @PostMapping(Routes.OUTBOUND_ORDER)
  public ResponseEntity<OutboundOrderResponseDTO> save(@RequestBody @Valid OutboundOrderRequestDTO outboundOrder) {
    return new ResponseEntity<>(service.save(outboundOrder), HttpStatus.CREATED);
  }

  /**
   * update
   * Replace an existing Outbound Order in the database, and return the Outbound Order data
   * The request must be a PUT with a OutboundOrderRequestDTO object in the request body
   *
   * @param outboundOrder the Outbound Order data to be replaced
   * @return ResponseEntity<OutboundOrderResponseDTO>  response entity
   */
  @PutMapping(Routes.OUTBOUND_ORDER)
  public ResponseEntity<OutboundOrderResponseDTO> update(@RequestBody @Valid OutboundOrderRequestDTO outboundOrder) {
    return new ResponseEntity<>(service.update(outboundOrder), HttpStatus.CREATED);
  }
}