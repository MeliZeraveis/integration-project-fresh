package br.dh.meli.integratorprojectfresh.controller;


import br.dh.meli.integratorprojectfresh.dto.response.InboundOrderPutResponseDTO;
import br.dh.meli.integratorprojectfresh.dto.request.InboundOrderRequestDTO;
import br.dh.meli.integratorprojectfresh.dto.response.InboundOrderPostResponseDTO;

import br.dh.meli.integratorprojectfresh.enums.Routes;

import br.dh.meli.integratorprojectfresh.service.InboundOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/fresh-product")
public class InboundOrderController {

    @Autowired
    private InboundOrderService inboundOrderService;

    @PostMapping("/inboundorder")

    public ResponseEntity<InboundOrderPostResponseDTO> save(@RequestBody @Valid InboundOrderRequestDTO inboundOrder) {
        return new ResponseEntity<>(inboundOrderService.save(inboundOrder), HttpStatus.CREATED);
    }


    @PutMapping("/inboundorder")
    public ResponseEntity<InboundOrderPutResponseDTO> update(@RequestBody @Valid InboundOrderRequestDTO inboundOrder) {
        return new ResponseEntity<>(inboundOrderService.update(inboundOrder), HttpStatus.CREATED);
    }
}
