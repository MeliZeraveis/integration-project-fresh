package br.dh.meli.integratorprojectfresh.controller;

import br.dh.meli.integratorprojectfresh.dto.response.WarehouseProductQuantityGetResponseDTO;
import br.dh.meli.integratorprojectfresh.service.IWareshouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api/v1/fresh-products/warehouse")
public class WarehouseController {

    @Autowired
    private IWareshouseService service;

    @GetMapping("/query_type")
    ResponseEntity<WarehouseProductQuantityGetResponseDTO> getAnnouncementByAnnouncementId(@RequestParam Long product_id) {
        WarehouseProductQuantityGetResponseDTO warehouseProductQuantityList = service.getWarehouseProductsQuantityListByAnnoucementId(product_id);
        return new ResponseEntity<>(warehouseProductQuantityList , HttpStatus.OK);
    }

}
