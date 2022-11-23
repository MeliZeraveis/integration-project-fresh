package br.dh.meli.integratorprojectfresh.controller;

import br.dh.meli.integratorprojectfresh.dto.response.BatchStockGetResponseDTO;
import br.dh.meli.integratorprojectfresh.service.IBatchStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/fresh-products/due-date")
public class BatchStockController {
   @Autowired
    private IBatchStockService service;

   @GetMapping
   @ResponseBody
    public ResponseEntity<BatchStockGetResponseDTO> getBatchStockByBatchStockId(@RequestParam Integer numberOfDays, @RequestParam Long sectionCode) {
         BatchStockGetResponseDTO batchStock = service.getBatchStockByBatchStockId(numberOfDays, sectionCode);
         return new ResponseEntity<>(batchStock, HttpStatus.OK);
    }

    @GetMapping("/list")
    @ResponseBody
    public ResponseEntity<BatchStockGetResponseDTO> findBatchStockByBatchStockNumber(@RequestParam Integer numberOfDays, @RequestParam String category, @RequestParam String order) {
         BatchStockGetResponseDTO batchStock = service.findBatchStockByBatchStockNumber(numberOfDays, category, order);
         return new ResponseEntity<>(batchStock, HttpStatus.OK);
    }
}
