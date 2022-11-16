package br.dh.meli.integratorprojectfresh.service;

import br.dh.meli.integratorprojectfresh.dto.response.BatchStockGetResponseDTO;

import java.util.List;

public interface IBatchStockService {
    BatchStockGetResponseDTO getBatchStockByBatchStockId(Integer numberOfDays, String section);

    BatchStockGetResponseDTO findBatchStockByBatchStockNumber(Integer numberOfDays, String category);

}
