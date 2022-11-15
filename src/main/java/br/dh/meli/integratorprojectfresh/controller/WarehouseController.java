package br.dh.meli.integratorprojectfresh.controller;

import br.dh.meli.integratorprojectfresh.dto.response.WarehouseProductQuantityListByAnnoucementIdGetResponseDTO;
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

    @GetMapping("/{product_id}")
    ResponseEntity<WarehouseProductQuantityListByAnnoucementIdGetResponseDTO> getAnnouncementByAnnouncementId(@PathVariable Long product_id) {
        WarehouseProductQuantityListByAnnoucementIdGetResponseDTO warehouseProductQuantityList = service.getWarehouseProductsQuantityListByAnnoucementId(product_id);
        return new ResponseEntity<>(warehouseProductQuantityList , HttpStatus.OK);
    }

}
