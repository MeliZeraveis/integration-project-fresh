package br.dh.meli.integratorprojectfresh.controller;

import br.dh.meli.integratorprojectfresh.dto.response.BatchStockGetResponseDTO;
import br.dh.meli.integratorprojectfresh.service.IBatchStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * The type Batch stock controller.
 */
@RestController
@RequestMapping("/api/v1/fresh-products/due-date")
public class BatchStockController {
   @Autowired
    private IBatchStockService service;

    /**
     * Gets batch stock by batch stock id.
     *
     * @param numberOfDays the number of days
     * @param sectionCode  the section code
     * @return the batch stock by batch stock id
     */
    @GetMapping
   @ResponseBody
    public ResponseEntity<BatchStockGetResponseDTO> getBatchStockByBatchStockId(@RequestParam Integer numberOfDays, @RequestParam Long sectionCode) {
         BatchStockGetResponseDTO batchStock = service.getBatchStockByBatchStockId(numberOfDays, sectionCode);
         return new ResponseEntity<>(batchStock, HttpStatus.OK);
    }

    /**
     * Find batch stock by batch stock number response entity.
     *
     * @param numberOfDays the number of days
     * @param category     the category
     * @param order        the order
     * @return the response entity
     */
    @GetMapping("/list")
    @ResponseBody
    public ResponseEntity<BatchStockGetResponseDTO> findBatchStockByBatchStockNumber(@RequestParam Integer numberOfDays, @RequestParam String category, @RequestParam String order) {
         BatchStockGetResponseDTO batchStock = service.findBatchStockByBatchStockNumber(numberOfDays, category, order);
         return new ResponseEntity<>(batchStock, HttpStatus.OK);
    }
}
