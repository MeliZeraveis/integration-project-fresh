package br.dh.meli.integratorprojectfresh.controller;

import br.dh.meli.integratorprojectfresh.dto.response.BatchStockGetResponseDTO;
import br.dh.meli.integratorprojectfresh.service.IBatchStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/fresh-products/due-date")
public class BatchStockController {
   @Autowired
    private IBatchStockService service;

   @GetMapping
   @ResponseBody
    public ResponseEntity<List<BatchStockGetResponseDTO>> getBatchStockByBatchStockId(@RequestParam Integer numberOfDays, @RequestParam String section) {
         List<BatchStockGetResponseDTO> batchStock = service.getBatchStockByBatchStockId(numberOfDays, section);
         return new ResponseEntity<>(batchStock, HttpStatus.OK);
    }

    @GetMapping("/list")
    @ResponseBody
    public ResponseEntity<List<BatchStockGetResponseDTO>> findBatchStockByBatchStockNumber(@RequestParam Integer numberOfDays, @RequestParam String category) {
         List<BatchStockGetResponseDTO> batchStock = service.findBatchStockByBatchStockNumber(numberOfDays, category);
         return new ResponseEntity<>(batchStock, HttpStatus.OK);
    }
}
