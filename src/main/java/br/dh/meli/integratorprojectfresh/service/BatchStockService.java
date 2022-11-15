package br.dh.meli.integratorprojectfresh.service;

import br.dh.meli.integratorprojectfresh.dto.response.AnnoucementGetResponseDTO;
import br.dh.meli.integratorprojectfresh.dto.response.BatchStockGetResponseDTO;
import br.dh.meli.integratorprojectfresh.enums.Msg;
import br.dh.meli.integratorprojectfresh.exception.NotFoundException;
import br.dh.meli.integratorprojectfresh.model.BatchStock;
import br.dh.meli.integratorprojectfresh.repository.BatchStockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BatchStockService implements IBatchStockService {

    private final BatchStockRepository repo;
    @Override
    public List<BatchStockGetResponseDTO> getBatchStockByBatchStockId(Integer numberOfDays, String section) {
        List<BatchStock> batchStock = repo.findAllByDueDateAndSectionType(LocalDate.now().plusDays(numberOfDays), section);
        if(batchStock.isEmpty()) {
            throw new NotFoundException(Msg.BATCH_NOT_FOUND);
        }
        List<BatchStockGetResponseDTO> responseDTO = batchStock.stream().map(BatchStockGetResponseDTO::new).collect(Collectors.toList());
        return responseDTO;

    }

    @Override
    public List<BatchStockGetResponseDTO> findBatchStockByBatchStockNumber(Integer numberOfDays, String category) {
        List<BatchStock> batchStock = repo.findAllByDueDateAndCategory(LocalDate.now().plusDays(numberOfDays), category);
        if(batchStock.isEmpty()) {
            throw new NotFoundException(Msg.SECTION_NOT_FOUND);
        }
        if(category.equalsIgnoreCase("FS")|| category.equalsIgnoreCase("RF") || category.equalsIgnoreCase("FF")) {
            List<BatchStockGetResponseDTO> responseDTO = batchStock.stream().map(BatchStockGetResponseDTO::new).collect(Collectors.toList());
            return responseDTO;
        }
        else {
            throw new NotFoundException(Msg.SECTION_NOT_FOUND);
        }
    }
}
