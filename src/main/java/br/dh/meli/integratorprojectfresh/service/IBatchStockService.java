package br.dh.meli.integratorprojectfresh.service;

import br.dh.meli.integratorprojectfresh.dto.response.BatchStockGetResponseDTO;

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

}
