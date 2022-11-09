package br.dh.meli.integratorprojectfresh.controller;

import br.dh.meli.integratorprojectfresh.dto.InboundOrderPutResponseDTO;
import br.dh.meli.integratorprojectfresh.dto.InboundOrderRequestDTO;
import br.dh.meli.integratorprojectfresh.dto.InboundOrderPostResponseDTO;
import br.dh.meli.integratorprojectfresh.enums.Routes;
import br.dh.meli.integratorprojectfresh.service.InboundOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Routes.BASE_ROUTE)
public class InboundOrderController {

    @Autowired
    private InboundOrderService inboundOrderService;

    @PostMapping(Routes.INBOUND_ORDER)

    public ResponseEntity<InboundOrderPostResponseDTO> save(@RequestBody InboundOrderRequestDTO inboundOrder) {
        return new ResponseEntity<>(inboundOrderService.save(inboundOrder), HttpStatus.CREATED);
    }

    // @PutMapping(Routes.INBOUND_ORDER + "/{orderNumber}") - TODO: implementar assim?
    @PutMapping(Routes.INBOUND_ORDER)
    public ResponseEntity<InboundOrderPutResponseDTO> update(@RequestBody InboundOrderRequestDTO inboundOrder) {
        return new ResponseEntity<>(inboundOrderService.update(inboundOrder), HttpStatus.CREATED);
    }
}
