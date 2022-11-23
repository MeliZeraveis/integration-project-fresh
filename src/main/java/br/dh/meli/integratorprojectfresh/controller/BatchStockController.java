package br.dh.meli.integratorprojectfresh.controller;

import br.dh.meli.integratorprojectfresh.dto.response.BatchStockGetResponseDTO;
import br.dh.meli.integratorprojectfresh.enums.Routes;
import br.dh.meli.integratorprojectfresh.service.IBatchStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

/**
 * The Batch Stock controller.
 */
@RestController
@RequestMapping(Routes.BASE_ROUTE)
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
  @GetMapping(Routes.DUE_DATE)
  @ResponseBody
  public ResponseEntity<BatchStockGetResponseDTO> getBatchStockByBatchStockId(@RequestParam Integer numberOfDays, @RequestParam Long sectionCode) {
    return ResponseEntity.ok(service.getBatchStockByBatchStockId(numberOfDays, sectionCode));
  }

  /**
   * Find batch stock by batch stock number response entity.
   *
   * @param numberOfDays the number of days
   * @param category     the category
   * @param order        the order
   * @return the response entity
   */
  @GetMapping(Routes.DUE_DATE_LIST)
  @ResponseBody
  public ResponseEntity<BatchStockGetResponseDTO> findBatchStockByBatchStockNumber(@RequestParam Integer numberOfDays,
                                                                                   @RequestParam String category,
                                                                                   @RequestParam String order) {
    return ResponseEntity.ok(service.findBatchStockByBatchStockNumber(numberOfDays, category, order));
  }

  /**
   * Find expired batch stock response entity.
   *
   * @param date the date
   * @return the response entity
   */
  @GetMapping(Routes.DUE_DATE_EXPIRED)
  @ResponseBody
  public ResponseEntity<BatchStockGetResponseDTO> findExpiredBatchStock(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                                                        @RequestParam(required = false) LocalDate date,
                                                                        @RequestParam(required = false) String category,
                                                                        @RequestParam(required = false) String sortBy) {
    return ResponseEntity.ok(service.findExpiredBatchStock(date != null ? date : LocalDate.now(), category,
            (sortBy != null ? sortBy : "asc")));
  }
}
