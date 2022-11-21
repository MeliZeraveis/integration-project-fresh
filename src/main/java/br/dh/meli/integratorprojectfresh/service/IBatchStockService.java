package br.dh.meli.integratorprojectfresh.service;

import br.dh.meli.integratorprojectfresh.dto.response.BatchStockGetResponseDTO;

import java.time.LocalDate;
import java.util.List;

/**
 * The interface Batch stock service.
 */
public interface IBatchStockService {
    /**
     * Gets batch stock by batch stock id.
     *
     * @param numberOfDays the number of days
     * @param sectionCode  the section code
     * @return the batch stock by batch stock id
     */
    BatchStockGetResponseDTO getBatchStockByBatchStockId(Integer numberOfDays, Long sectionCode);

    /**
     * Find batch stock by batch stock number batch stock get response dto.
     *
     * @param numberOfDays the number of days
     * @param category     the category
     * @param order        the order
     * @return the batch stock get response dto
     */
    BatchStockGetResponseDTO findBatchStockByBatchStockNumber(Integer numberOfDays, String category, String order);

    /**
     * Find all expired batches relative to an (optional) date.
     *
     * @param date the expiration date (defaults to now)
     * @return the expired batches
     */
    BatchStockGetResponseDTO findExpiredBatchStock(LocalDate date, String category, String sortBy);
}
